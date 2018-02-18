package io.github.dawncraft.potion;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion
{
    public static int nextIndex = 0;

    // 内部使用,需要重写
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
        return super.setIconIndex(index, 0);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(this.getPotionTexture(effect));
        int u = this.getStatusIconIndex() % 16;
        int v = Math.floorDiv(this.getStatusIconIndex(), 16);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, u, v, 16, 16);
    }
    
    public ResourceLocation getPotionTexture(PotionEffect effect)
    {
        return PotionLoader.POTION_TEXTURE;
    }
}
