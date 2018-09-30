package io.github.dawncraft.potion;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion
{
    private static final ResourceLocation POTION_TEXTURE = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/potion.png");
    private static int nextIndex = 0;

    private int statusIconX = 0;
    private int statusIconY = 0;
    
    protected PotionBase(String id, boolean badEffect, int potionColor)
    {
        this(new ResourceLocation(Dawncraft.MODID + ":" + id), badEffect, potionColor);
        this.setIconIndex(nextIndex++);
    }

    public PotionBase(ResourceLocation location, boolean badEffect, int potionColor)
    {
        super(location, badEffect, potionColor);
    }

    public Potion setIconIndex(int index)
    {
        int x = index % 16;
        int y = Math.floorDiv(index, 16);
        return this.setIconIndex(x, y);
    }
    
    @Override
    public Potion setIconIndex(int x, int y)
    {
        this.statusIconX = x;
        this.statusIconY = y;
        return this;
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
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(this.getPotionTexture(effect));
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, this.statusIconX * 16, this.statusIconY * 16, 16, 16);
    }

    public ResourceLocation getPotionTexture(PotionEffect effect)
    {
        return POTION_TEXTURE;
    }
}
