package io.github.dawncraft.client.renderer.texture;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.renderer.skill.SkillRenderLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 注册材质，需重写
 * <br>builtin方块的破坏粒子在{#link net.minecraft.client.renderer.BlockModelShapes.getTexture(IBlockState)}</br>
 * 
 * @author QingChenW
 */
@Deprecated
public class TextureLoader
{
    public static final ResourceLocation skillsTexture = new ResourceLocation(Dawncraft.MODID, "textures/atlas/skills.png");
    private static TextureMap textureMapSkills;

    public TextureLoader(FMLPreInitializationEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();

        textureMapSkills = new TextureMap("textures", false);
        textureMapSkills.setMipmapLevels(mc.gameSettings.mipmapLevels);
        textureMapSkills.setBlurMipmapDirect(false, mc.gameSettings.mipmapLevels > 0);
    }

    public static void loadTextureMap()
    {
        Minecraft mc = Minecraft.getMinecraft();
        
        mc.renderEngine.loadTickableTexture(skillsTexture, textureMapSkills);
        textureMapSkills.loadSprites(mc.getResourceManager(), new IIconCreator()
        {
            @Override
            public void registerSprites(TextureMap map)
            {
                for(ResourceLocation res : SkillRenderLoader.getTextures())
                {
                    map.registerSprite(getActualLocation(res));
                }
            }
        });
    }
    
    public static ResourceLocation getActualLocation(ResourceLocation location)
    {
        return new ResourceLocation(location.getResourceDomain(), "skills/" + location.getResourcePath());
    }
    
    public static TextureMap getTextureMapSkills()
    {
        return TextureLoader.textureMapSkills;
    }
}
