package com.github.wdawning.dawncraft.extend;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public  class ExtendedPlayer implements IExtendedEntityProperties
{
public final static String EXT_PROP_NAME = "ExtendedPlayer";
private final EntityPlayer player;
private final int maxMana = 20;
private int currentMana;

public ExtendedPlayer(EntityPlayer player)
{
this.player = player;
this.currentMana = this.maxMana;
}

/**
* Used to register these extended properties for the player during EntityConstructing event
* This method is for convenience only; it will make your code look nicer
*/
public static void register(EntityPlayer player)
{
player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayer(player));
}

/**
* Returns ExtendedPlayer properties for player
* This method is for convenience only; it will make your code look nicer
*/
public static ExtendedPlayer get(EntityPlayer player)
{
return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
}

@Override
public void saveNBTData(NBTTagCompound compound)
{
NBTTagCompound properties = new NBTTagCompound();

properties.setInteger("Mana", this.currentMana);

compound.setTag(EXT_PROP_NAME, properties);
}

@Override
public void loadNBTData(NBTTagCompound compound)
{
	if(compound.hasKey(EXT_PROP_NAME, Constants.NBT.TAG_COMPOUND))
	{
		NBTTagCompound properties = compound.getCompoundTag(EXT_PROP_NAME);

		this.currentMana = properties.getInteger("Mana");
	}
}

@Override
public void init(Entity entity, World world)
{
	
}

/**
* Returns true if the amount of mana was consumed or false
* if the player's current mana was insufficient
*/
public boolean consumeMana(int amount)
{
boolean flag = this.currentMana >= amount;

if(flag)
{
this.currentMana = this.currentMana - amount;
}

return flag;
}

/**
* Simple method sets mana
*/
public void setMana(int amount)
{
if(amount <= 20 && amount >= 0)
{
this.currentMana = amount;
}
}

public int getMana()
{
return this.currentMana;
}

/**
* Simple method sets current mana to max mana
*/
public void replenishMana()
{
this.currentMana = this.maxMana;
}
}