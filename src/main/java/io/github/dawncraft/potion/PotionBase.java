package io.github.dawncraft.potion;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion
{
    private int index;

    public PotionBase(String id, boolean badEffect, int potionColor)
    {
        this(new ResourceLocation(Dawncraft.MODID + ":" + id), badEffect, potionColor, PotionLoader.nextIndex++);
    }
    
    public PotionBase(ResourceLocation location, boolean badEffect, int potionColor, int iconIndex)
    {
        super(location, badEffect, potionColor);
        this.index = iconIndex;
    }
    
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(this.getPotionTexture(effect));
        int u = this.index % 16;
        int v = Math.floorDiv(this.index, 16);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, u, v, 16, 16);
    }
    
    public ResourceLocation getPotionTexture(PotionEffect effect)
    {
        return PotionLoader.POTION_TEXTURE;
    }
}
