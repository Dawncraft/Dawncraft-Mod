package io.github.dawncraft.client.renderer.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.ModData;
import io.github.dawncraft.client.renderer.skill.SkillModelMesher;
import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.skill.Skill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.IRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

/**
 * 曙光工艺Mod的模型加载器,加载技能物品的模型
 *
 * @author QingChenW
 */
public class ModelLoader
{
    public static ModelLoader modelLoader;
    private static boolean hasInited = false;
    private final Map<ModelResourceLocation, IModel> stateModels = Maps.newHashMap();
    private final Set<ResourceLocation> textures = Sets.newHashSet();
    private final Set<ResourceLocation> loadingModels = Sets.newHashSet();

    private ProgressBar skillBar;

    public static void initModelLoader()
    {
        modelLoader = new ModelLoader();

        OBJLoader.instance.addDomain(Dawncraft.MODID);
        B3DLoader.instance.addDomain(Dawncraft.MODID);
        
        MinecraftForge.EVENT_BUS.register(modelLoader);
    }

    /**
     *
     * 在 {@link net.minecraftforge.client.model.ModelLoader#setupModelRegistry()} 之后进行烘制技能的模型
     *
     * @param event
     */
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        net.minecraftforge.client.model.ModelLoader modelLoader = event.modelLoader;
        IRegistry<ModelResourceLocation, IBakedModel> modelRegistry = event.modelRegistry;
        
        /*
         * 初始化这个事件的监听器必须在preInit阶段,然而ModelLoader依赖的TextureLoader的初始化需要TextureManager必须在Init阶段进行
         * 我是没办法了
         */
        if(!hasInited)
        {
            new TextureLoader();
            hasInited = true;
        }

        List<String> skillVariants = Lists.newArrayList();
        for(Skill skill : ModData.getSkillRegistry().typeSafeIterable())
        {
            skillVariants.add(((ResourceLocation)Skill.skillRegistry.getNameForObject(skill)).toString());
        }
        Collections.sort(skillVariants);
        
        this.skillBar = ProgressManager.push("DawnModelLoader: skills", skillVariants.size());

        for(String s : skillVariants)
        {
            ResourceLocation file = this.getSkillLocation(s);
            ModelResourceLocation memory = net.minecraftforge.client.model.ModelLoader.getInventoryVariant(s);
            this.skillBar.step(memory.toString());
            IModel model = null;
            try
            {
                // default loading
                model = this.getModel(file);
                if (model == null)
                {
                    model = modelLoader.getMissingModel();
                }
                this.stateModels.put(memory, model);
            }
            catch (FileNotFoundException exception)
            {
                new Exception("Could not load skill model from the normal location " + file + ": " + exception).printStackTrace();;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        ProgressManager.pop(this.skillBar);
        
        TextureLoader.getTextureLoader().getTextureMapSkills().loadSprites(mc.getResourceManager(), new IIconCreator()
        {
            @Override
            public void registerSprites(TextureMap map)
            {
                for(ResourceLocation res : ModelLoader.this.textures)
                {
                    map.registerSprite(res);
                }
            }
        });
        IFlexibleBakedModel missingBaked = modelLoader.getMissingModel().bake(modelLoader.getMissingModel().getDefaultState(),
                DefaultVertexFormats.ITEM, net.minecraftforge.client.model.ModelLoader.defaultTextureGetter());
        for (Entry<ModelResourceLocation, IModel> e : this.stateModels.entrySet())
        {
            if(e.getValue() == modelLoader.getMissingModel())
            {
                modelRegistry.putObject(e.getKey(), missingBaked);
            }
            else
            {
                modelRegistry.putObject(e.getKey(), e.getValue().bake(e.getValue().getDefaultState(),
                        DefaultVertexFormats.ITEM, net.minecraftforge.client.model.ModelLoader.defaultTextureGetter()));
            }
        }
    }

    protected ResourceLocation getSkillLocation(String location)
    {
        ResourceLocation resourcelocation = new ResourceLocation(location.replaceAll("#.*", ""));
        return new ResourceLocation(resourcelocation.getResourceDomain(), "skill/" + resourcelocation.getResourcePath());
    }
    
    public IModel getModel(ResourceLocation location) throws IOException
    {
        if(!ModelLoaderRegistry.loaded(location)) this.loadAnyModel(location);
        return ModelLoaderRegistry.getModel(location);
    }

    private void loadAnyModel(ResourceLocation location) throws IOException
    {
        if(this.loadingModels.contains(location))
        {
            throw new IllegalStateException("circular model dependencies involving model " + location);
        }
        this.loadingModels.add(location);
        try
        {
            IModel model = ModelLoaderRegistry.getModel(location);
            this.textures.addAll(model.getTextures());
        }
        finally
        {
            this.loadingModels.remove(location);
        }
    }
    
    // API
    private static final Map<RegistryDelegate<Skill>, SkillMeshDefinition> customMeshDefinitions = Maps.newHashMap();
    private static final Map<Pair<RegistryDelegate<Skill>, Integer>, ModelResourceLocation> customModels = Maps.newHashMap();
    
    /**
     * Adds a simple mapping from Skill + level to the model variant.
     * Registers the variant with the ModelBakery too.
     */
    public static void setCustomModelResourceLocation(Skill skill, int level, ModelResourceLocation model)
    {
        customModels.put(Pair.of(skill.delegate, level), model);
    }
    
    /**
     * Adds generic SkillStack -> model variant logic.
     * You still need to manually call registerSkillVariants with all values that meshDefinition can return.
     */
    public static void setCustomMeshDefinition(Skill skill, SkillMeshDefinition meshDefinition)
    {
        customMeshDefinitions.put(skill.delegate, meshDefinition);
    }
    
    public static void onRegisterSkills(SkillModelMesher mesher)
    {
        for (Map.Entry<RegistryDelegate<Skill>, SkillMeshDefinition> e : customMeshDefinitions.entrySet())
        {
            mesher.register(e.getKey().get(), e.getValue());
        }
        for (Entry<Pair<RegistryDelegate<Skill>, Integer>, ModelResourceLocation> e : customModels.entrySet())
        {
            mesher.register(e.getKey().getLeft().get(), e.getKey().getRight(), e.getValue());
        }
    }
}
