package io.github.dawncraft.potion;

import io.github.dawncraft.dawncraft;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBadGer extends Potion
{
    private static final ResourceLocation res = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/potion.png");
    
    public PotionBadGer()
	{
        super(new ResourceLocation(dawncraft.MODID + ":" + "bad_ger"), true, 0x7F0000);
        this.setPotionName("potion.badGer");
        //this.setIconIndex(0, 0);
	}

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(PotionBadGer.res);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }
}
