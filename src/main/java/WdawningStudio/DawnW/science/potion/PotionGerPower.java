package WdawningStudio.DawnW.science.potion;

import WdawningStudio.DawnW.science.science;
import WdawningStudio.DawnW.science.common.ConfigLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionGerPower extends Potion
{
    private static final ResourceLocation res = new ResourceLocation(science.MODID + ":" + "textures/gui/potion.png");
	
    public PotionGerPower()
    {
        super(ConfigLoader.potionGerPowerID, new ResourceLocation(science.MODID + ":" + "ger_power"), true, 0x7F0000);
        this.setPotionName("potion.gerPower");
        //this.setIconIndex(0, 0);
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(PotionGerPower.res);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }
}