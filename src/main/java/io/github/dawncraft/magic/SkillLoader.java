package io.github.dawncraft.magic;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author QingChenW
 *
 */
public class SkillLoader
{
    public static final Skill[] magicTypes = new Skill[64];
    public static Skill attack;
    public static Skill heal;
    
	public SkillLoader(FMLPreInitializationEvent event)
	{
		attack = new Skill(0, 4).setMagicName("magic.attack.name");
		heal = new Skill(1, 4).setMagicName("magic.heal.name");
	}
}
