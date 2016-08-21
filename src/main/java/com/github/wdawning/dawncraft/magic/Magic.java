package com.github.wdawning.dawncraft.magic;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.extend.ExtendedPlayer;
import com.github.wdawning.dawncraft.potion.PotionLoader;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Magic
{
    public static final Magic[] magicTypes = new Magic[64];
    public static final Magic attack = new Magic(0, 4).setMagicName("magic.attack.name");
    public static final Magic heal = new Magic(1, 4).setMagicName("magic.heal.name");
    
    public final int id;
    public final int mana;
    public static String name = "";
    
    public Magic(int magicID, int magicMANA)
    {
        this.id = magicID;
        this.mana = magicMANA;
        magicTypes[magicID] = this;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public int getMANA()
    {
        return this.mana;
    }

    public Magic setMagicName(String nameIn)
    {
        this.name = nameIn;
        return this;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public static Magic getMagicType(int meta)
    {
    	switch(meta)
    	{
    	case 0:return attack;
    	case 1:return heal;
    	}
		return null;
    }
    
    public void spellMagic(Magic magic, ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        ExtendedPlayer props = ExtendedPlayer.get(playerIn);
        
    	if(magic == attack)
    	{
        	boolean flag = props.consumeMana(magic.getMANA());
        	if(flag)
        	{
        		playerIn.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 480, 2));
        	}
    	}
    	else if(magic == heal)
    	{
        	boolean flag = props.consumeMana(magic.getMANA());
        	if(flag)
        	{
        		playerIn.heal(12.0F);
        	}
    	}
    }
}
