package com.github.wdawning.dawncraft.item;

import net.minecraft.item.Item;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemComputerCPU extends Item
{
	//Simple computer CPU
    public static class SimpleCPU extends ItemComputerCPU
    {
    	public SimpleCPU()
    	{
    		super();
    		this.setUnlocalizedName("simpleCPU");
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }
    
	//High computer CPU
    public static class HighCPU extends ItemComputerCPU
    {
    	public HighCPU()
    	{
    		super();
    		this.setUnlocalizedName("highCPU");
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }
    
	//Pro computer CPU
    public static class ProCPU extends ItemComputerCPU
    {
    	public ProCPU()
    	{
    		super();
    		this.setUnlocalizedName("proCPU");
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }
    
	//Super computer CPU
    public static class SuperCPU extends ItemComputerCPU
    {
    	public SuperCPU()
    	{
    		super();
    		this.setUnlocalizedName("superCPU");
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }
}
