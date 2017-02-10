package io.github.dawncraft.magic;

/**
 * @author QingChenW
 *
 */
public class SkillLoader
{
    public static final MagicSkill[] magicTypes = new MagicSkill[64];
    public static final MagicSkill attack = new MagicSkill(0, 4).setMagicName("magic.attack.name");
    public static final MagicSkill heal = new MagicSkill(1, 4).setMagicName("magic.heal.name");
}
