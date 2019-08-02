package io.github.dawncraft.client.renderer.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.renderer.skill.ModelSkill;
import io.github.dawncraft.client.renderer.skill.SkillMeshDefinition;
import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.ITextureMapPopulator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IRegistryDelegate;

/**
 * Load skill's models.
 *
 * @author QingChenW
 */
public class ModelLoader
{
    private Minecraft mc;
    private TextureLoader textureLoader;

    private final Map<ResourceLocation, ModelSkill> models = Maps.newHashMap();
    private final Set<ResourceLocation> textures = Sets.newHashSet();
    private final Set<ResourceLocation> loadingModels = Sets.newHashSet();
    private Map<ResourceLocation, Exception> loadingExceptions;

    private ProgressBar skillBar;

    public ModelLoader(TextureLoader textureLoader)
    {
        this.mc = Minecraft.getMinecraft();
        this.textureLoader = textureLoader;

        OBJLoader.INSTANCE.addDomain(Dawncraft.MODID);
        B3DLoader.INSTANCE.addDomain(Dawncraft.MODID);

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * After {@link net.minecraftforge.client.model.ModelLoader#setupModelRegistry()}
     *
     * @param event
     */
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event)
    {
        // 获得ModelLoader的loadingExceptions字段
        this.loadingExceptions = ReflectionHelper.getPrivateValue(net.minecraftforge.client.model.ModelLoader.class,
                event.getModelLoader(), "loadingExceptions");

        // FIXME TextureLoader需要的TextureManager在Init阶段进行初始化
        this.textureLoader.initTextures();

        List<String> skills = Lists.newArrayList();
        for (Skill skill : Skill.REGISTRY)
        {
            skills.add(Skill.REGISTRY.getNameForObject(skill).toString());
        }
        Collections.sort(skills);

        this.skillBar = ProgressManager.push("DawncraftModelLoader: skills", skills.size());

        for (String skill : skills)
        {
            ModelResourceLocation location = new ModelResourceLocation(skill);
            ResourceLocation file = this.getSkillLocation(skill);

            this.skillBar.step(location.toString());

            try
            {
                if (!this.models.containsKey(file))
                    // default loading
                    this.loadAnyModel(file);
            }
            catch (FileNotFoundException exception)
            {
                this.storeException(location, new Exception("Could not load skill model from the normal location " + file + ": " + exception));
            }
            catch (Exception exception)
            {
                this.storeException(location, exception);
            }
        }

        ProgressManager.pop(this.skillBar);

        this.textureLoader.getTextureMapSkills().loadSprites(this.mc.getResourceManager(), new ITextureMapPopulator()
        {
            @Override
            public void registerSprites(TextureMap map)
            {
                for (ResourceLocation res : ModelLoader.this.textures)
                {
                    map.registerSprite(res);
                }
            }
        });
    }

    protected void storeException(ResourceLocation location, Exception exception)
    {
        this.loadingExceptions.put(location, exception);
    }

    protected ResourceLocation getSkillLocation(String location)
    {
        ResourceLocation res = new ResourceLocation(location.replaceAll("#.*", ""));
        return new ResourceLocation(res.getNamespace(), "skill/" + res.getPath());
    }

    public void loadAnyModel(ResourceLocation location) throws IOException
    {
        if (this.loadingModels.contains(location))
        {
            throw new IllegalStateException("circular model dependencies involving model " + location);
        }
        this.loadingModels.add(location);
        try
        {
            ModelSkill modelSkill = this.loadModel(location);
            this.models.put(location, modelSkill);
            for (String texture : (Iterable<String>) modelSkill.textures.values())
            {
                if (!texture.startsWith("#"))
                {
                    this.textures.add(new ResourceLocation(texture));
                }
            }
        }
        finally
        {
            this.loadingModels.remove(location);
        }
    }

    public ModelSkill loadModel(ResourceLocation location) throws IOException
    {
        ResourceLocation modelLocation = new ResourceLocation(ModelLoaderRegistry.getActualLocation(location) + ".json");
        IResource resource = this.mc.getResourceManager().getResource(modelLocation);
        Reader reader = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);

        ModelSkill modelSkill;
        try
        {
            modelSkill = ModelSkill.deserialize(reader);
            modelSkill.name = location.toString();
        }
        finally
        {
            reader.close();
        }

        return modelSkill;
    }

    public ModelSkill getSkillModel(Skill skill)
    {
        ResourceLocation location = this.getSkillLocation(Skill.REGISTRY.getNameForObject(skill).toString());
        ModelSkill modelSkill = this.models.get(location);
        return modelSkill != null ? modelSkill : null;
        // 增加默认技能模型
    }

    public ResourceLocation getSkillTexture(SkillStack skillStack)
    {
        ResourceLocation location = null;

        if (customMeshDefinitions.containsKey(skillStack.getSkill().delegate))
        {
            location = customMeshDefinitions.get(skillStack.getSkill().delegate).getTextureLocation(skillStack);
        }

        if (location == null)
        {
            ModelSkill modelSkill = this.getSkillModel(skillStack.getSkill());
            if (modelSkill != null)
            {
                String s = modelSkill.resolveTextureName(String.valueOf(skillStack.getLevel()));
                location = !StringUtils.isNullOrEmpty(s) ? new ResourceLocation(s) : null;
            }
        }

        return location != null ? location : TextureMap.LOCATION_MISSING_TEXTURE;
    }

    public TextureAtlasSprite getSkillSprite(SkillStack skillStack)
    {
        ResourceLocation location = this.getSkillTexture(skillStack);
        return this.textureLoader.getTextureMapSkills().getAtlasSprite(location.toString());
    }

    // API
    private static final Map<IRegistryDelegate<Skill>, SkillMeshDefinition> customMeshDefinitions = Maps.newHashMap();

    /**
     * Adds generic SkillStack -> model variant logic.
     * You still need to manually call registerSkillVariants with all values that meshDefinition can return.
     */
    public static void setCustomMeshDefinition(Skill skill, SkillMeshDefinition meshDefinition)
    {
        customMeshDefinitions.put(skill.delegate, meshDefinition);
    }
}
