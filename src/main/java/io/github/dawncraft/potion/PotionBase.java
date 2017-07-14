package io.github.dawncraft.potion;

import io.github.dawncraft.dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion
{
    private static final ResourceLocation res = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/potion.png");
    
	public PotionBase(ResourceLocation location, boolean badEffect, int potionColor)
	{
		super(location, badEffect, potionColor);
	}

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(res);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }
}
