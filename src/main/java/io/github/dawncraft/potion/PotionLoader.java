package io.github.dawncraft.potion;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IEntityMana;
import io.github.dawncraft.entity.immortal.EntityImmortal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

/**
 * Register some potions.
 *
 * @author QingChenW
 */
public class PotionLoader
{
    public static Potion potionRecover = new PotionBase("recover", false, 0x0000FF)
    {
	@Override
	public boolean isReady(int duration, int amplifier)
	{
	    int k = 50 >> amplifier;
	    return k > 0 ? duration % k == 0 : true;
	};

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier)
	{
	    IEntityMana entityMana = null;

	    if (entity instanceof EntityPlayer)
	    {
		entityMana = entity.getCapability(CapabilityLoader.playerMagic, null);
	    }
	    else if (entity instanceof EntityImmortal)
	    {
		entityMana = (EntityImmortal) entity;
	    }

	    if (entityMana != null && entityMana.shouldRecover())
	    {
		entityMana.recover(1.0F);
	    }
	};
    }.setPotionName("potion.recover");
    public static Potion potionSilent = new PotionBase("silent", true, 0x585858).setPotionName("potion.silent");
    public static Potion potionParalysis = new PotionBase("paralysis", true, 0x3C64C8).setPotionName("potion.paralysis");
    public static Potion potionConfusion = new PotionBase("confusion", true, 0x649664).setPotionName("potion.confusion");

    public static Potion potionBrainDead = new PotionBase("brain_dead", true, 0x7F0000).setPotionName("potion.brainDead");
    public static Potion potionGerPower = new PotionBase("ger_power", false, 0x7F0000).setPotionName("potion.gerPower");
    public static Potion potionBadGer = new PotionBase("bad_ger", true, 0x7F0000).setPotionName("potion.badGer");

    public static void initPotions()
    {

    }
}
