package com.github.wdawning.dawncraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public  class ExtendedPlayer implements IExtendedEntityProperties
{
public final static String EXT_PROP_NAME = "ExtendedPlayer";
private final EntityPlayer player;
private float currentMana, maxMana;

public ExtendedPlayer(EntityPlayer player)
{
this.player = player;
this.currentMana = this.maxMana = 20;
}

/**
* Used to register these extended properties for the player during EntityConstructing event
* This method is for convenience only; it will make your code look nicer
*/
public static final void register(EntityPlayer player)
{
player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
}

/**
* Returns ExtendedPlayer properties for player
* This method is for convenience only; it will make your code look nicer
*/
public static final ExtendedPlayer get(EntityPlayer player)
{
return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
}

@Override
public void saveNBTData(NBTTagCompound compound)
{
NBTTagCompound properties = new NBTTagCompound();

properties.setFloat("CurrentMana", this.currentMana);
properties.setFloat("MaxMana", this.maxMana);

compound.setTag(EXT_PROP_NAME, properties);
}

@Override
public void loadNBTData(NBTTagCompound compound)
{
NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

this.currentMana = properties.getFloat("CurrentMana");
this.maxMana = properties.getFloat("MaxMana");
}

@Override
public void init(Entity entity, World world)
{
}

/**
* Returns true if the amount of mana was consumed or false
* if the player's current mana was insufficient
*/
public boolean consumeMana(float amount)
{
boolean flag = this.currentMana >= amount;

if(flag)
{
this.currentMana = this.currentMana - amount;
}

return flag;
}

/**
* Simple method sets current mana to max mana
*/
public void replenishMana()
{
this.currentMana = this.maxMana;
}
}