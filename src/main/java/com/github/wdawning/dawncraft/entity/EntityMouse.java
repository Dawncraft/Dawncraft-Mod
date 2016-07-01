package com.github.wdawning.dawncraft.entity;

import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.world.World;

public class EntityMouse extends EntityAmbientCreature
{
    public EntityMouse(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.6F);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
}
