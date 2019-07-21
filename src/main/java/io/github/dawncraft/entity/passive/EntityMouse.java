package io.github.dawncraft.entity.passive;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMouse extends EntityAmbientCreature
{
    public EntityMouse(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.6F);
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 4.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
}
