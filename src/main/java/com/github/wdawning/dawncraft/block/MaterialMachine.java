package com.github.wdawning.dawncraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialMachine extends Material
{
	public MaterialMachine()
	{
		super(MapColor.ironColor);
		this.setRequiresTool();
		this.setNoPushMobility();
	}
}
