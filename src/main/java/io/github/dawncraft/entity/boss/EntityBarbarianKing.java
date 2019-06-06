package io.github.dawncraft.entity.boss;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityBarbarianKing extends EntityCreature implements IBossDisplayData, IMob
{

    public EntityBarbarianKing(World world)
    {
        super(world);
    }
    
}
