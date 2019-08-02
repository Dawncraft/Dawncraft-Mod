package io.github.dawncraft.potion;

import javax.annotation.Nullable;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBase extends Potion
{
    private static final ResourceLocation POTION_TEXTURE = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/potion.png");

    public PotionBase(boolean badEffect, int potionColor)
    {
        super(badEffect, potionColor);
    }

    @Override
    public boolean isInstant()
    {
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return false;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health)
    {
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.getPotionTexture(effect));
        // mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, this.statusIconX * 16, this.statusIconY * 16, 16, 16);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha)
    {

    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getPotionTexture(PotionEffect effect)
    {
        return POTION_TEXTURE;
    }
}
