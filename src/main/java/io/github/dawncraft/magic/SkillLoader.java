package io.github.dawncraft.magic;

/**
 * @author QingChenW
 *
 */
public class SkillLoader
{
    public static final Skill[] magicTypes = new Skill[64];
    public static Skill attack;
    public static Skill heal;
    
	public SkillLoader()
	{
		attack = new Skill(0, 4).setMagicName("magic.attack.name");
		heal = new Skill(1, 4).setMagicName("magic.heal.name");
	}
}
