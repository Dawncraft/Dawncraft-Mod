package com.github.wdawning.dawncraft.potion;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.common.ConfigLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBrainDead extends Potion
{
    private static final ResourceLocation res = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/potion.png");
	
    public PotionBrainDead()
    {
        super(ConfigLoader.potionBrainDeadID, new ResourceLocation(dawncraft.MODID + ":" + "brain_dead"), true, 0x7F0000);
        this.setPotionName("potion.brainDead");
        //this.setIconIndex(0, 0);
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(PotionBrainDead.res);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }
}
