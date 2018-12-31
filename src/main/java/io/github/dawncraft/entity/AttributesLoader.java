package io.github.dawncraft.entity;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class AttributesLoader
{
    public static final IAttribute maxMana = new RangedAttribute((IAttribute)null, "generic.maxMana", 20.0D, 0.0D, 1024.0D).setDescription("Max Mana").setShouldWatch(true);
    
    public static final IAttribute spellSpeed = new RangedAttribute((IAttribute)null, "generic.spellSpeed", 0.0D, 0.0D, 2.0D).setDescription("Spell Speed");
    
    public static void initAttributes() {}
}
