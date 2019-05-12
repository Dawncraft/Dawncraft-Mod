package io.github.dawncraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

/**
 * 注册技能材质
 *
 * @author QingChenW
 */
public class TextureLoader
{
    public static TextureLoader textureLoader;
    public static final ResourceLocation locationPotionsTexture = new ResourceLocation("textures/atlas/potions.png");
    private TextureMap textureMapPotions;
    public static final ResourceLocation locationSkillsTexture = new ResourceLocation("textures/atlas/skills.png");
    private TextureMap textureMapSkills;

    public TextureLoader()
    {
        Minecraft mc = Minecraft.getMinecraft();
        
        this.textureMapPotions = new TextureMap("textures", true);
        this.textureMapPotions.setMipmapLevels(mc.gameSettings.mipmapLevels);
        mc.getTextureManager().loadTickableTexture(locationPotionsTexture, this.textureMapPotions);
        this.textureMapPotions.setBlurMipmapDirect(false, mc.gameSettings.mipmapLevels > 0);

        this.textureMapSkills = new TextureMap("textures", true);
        this.textureMapSkills.setMipmapLevels(mc.gameSettings.mipmapLevels);
        mc.getTextureManager().loadTickableTexture(locationSkillsTexture, this.textureMapSkills);
        this.textureMapSkills.setBlurMipmapDirect(false, mc.gameSettings.mipmapLevels > 0);
        
        textureLoader = this;
    }
    
    public TextureMap getTextureMapSkills()
    {
        return this.textureMapSkills;
    }
    
    public static TextureLoader getTextureLoader()
    {
        return textureLoader;
    }
}
