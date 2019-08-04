package io.github.dawncraft.client.renderer.texture;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

/**
 * Register some textures
 *
 * @author QingChenW
 */
public class TextureInit
{
    public static final ResourceLocation locationPotionsTexture = new ResourceLocation(Dawncraft.MODID, "textures/atlas/potions.png");
    public static final ResourceLocation locationSkillsTexture = new ResourceLocation(Dawncraft.MODID, "textures/atlas/skills.png");

    private static boolean hasInited = false;

    private Minecraft mc;
    private TextureMap textureMapPotions;
    private TextureMap textureMapSkills;

    public void initTextures()
    {
        if (hasInited) return;

        this.mc = Minecraft.getMinecraft();

        this.textureMapPotions = new TextureMap("textures", true);
        this.textureMapPotions.setMipmapLevels(this.mc.gameSettings.mipmapLevels);
        this.mc.getTextureManager().loadTickableTexture(locationPotionsTexture, this.textureMapPotions);
        this.textureMapPotions.setBlurMipmapDirect(false, this.mc.gameSettings.mipmapLevels > 0);

        this.textureMapSkills = new TextureMap("textures", true);
        this.textureMapSkills.setMipmapLevels(this.mc.gameSettings.mipmapLevels);
        this.mc.getTextureManager().loadTickableTexture(locationSkillsTexture, this.textureMapSkills);
        this.textureMapSkills.setBlurMipmapDirect(false, this.mc.gameSettings.mipmapLevels > 0);

        hasInited = true;
    }

    public TextureMap getTextureMapPotions()
    {
        return this.textureMapPotions;
    }

    public TextureMap getTextureMapSkills()
    {
        return this.textureMapSkills;
    }
}
