package io.github.dawncraft.entity;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class AttributesLoader
{
    public static final IAttribute maxMana = new RangedAttribute((IAttribute) null, "generic.maxMana", 20.0D, 0.0D, 1024.0D).setDescription("Max Mana").setShouldWatch(true);

    public static final IAttribute skillDamage = new RangedAttribute((IAttribute) null, "generic.skillDamage", 0.0D, 0.0D, 1.0D).setDescription("Skill Damage");
    public static final IAttribute spellSpeed = new RangedAttribute((IAttribute) null, "generic.spellSpeed", 0.0D, 0.0D, 2.0D).setDescription("Spell Speed");
    public static final IAttribute spellCooldown = new RangedAttribute((IAttribute) null, "generic.spellCooldown", 0.0D, 0.0D, 0.8D).setDescription("Spell Cooldown");
    
    public static void initAttributes() {}
}
