package io.github.dawncraft.block;

import com.google.common.base.Predicate;

import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.item.ItemSkull;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockSkullDawn extends BlockSkull
{
    private final static Predicate<BlockWorldState> IS_GERKING_SKULL = new Predicate<BlockWorldState>()
    {
        @Override
        public boolean apply(BlockWorldState blockWorldState)
        {
            return blockWorldState.getBlockState() != null && blockWorldState.getBlockState().getBlock() == BlockInit.skull && blockWorldState.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) blockWorldState.getTileEntity()).getSkullType() == 1;
        }
    };
    private BlockPattern kingBasePattern;
    private BlockPattern kingPattern;

    @Override
    public ItemSkull getSkullItem()
    {
        return (ItemSkull) ItemInit.skull;
    }

    @Override
    public BlockPattern getEntityBasePattern()
    {
        if (this.kingBasePattern == null)
        {
            this.kingBasePattern = FactoryBlockPattern.start().aisle(" ", "#", "*")
                    .where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.DIAMOND_BLOCK)))
                    .where('*', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.GOLD_BLOCK)))
                    .build();
        }
        return this.kingBasePattern;
    }

    @Override
    public BlockPattern getEntityPattern()
    {
        if (this.kingPattern == null)
        {
            this.kingPattern = FactoryBlockPattern.start().aisle("^", "#", "*")
                    .where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.DIAMOND_BLOCK)))
                    .where('*', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.GOLD_BLOCK)))
                    .where('^', BlockSkullDawn.IS_GERKING_SKULL)
                    .build();
        }
        return this.kingPattern;
    }

    @Override
    public void checkEntitySpawn(World world, BlockPos pos, TileEntitySkull tileentity)
    {
        if (!world.isRemote && tileentity.getSkullType() == 1 && pos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL)
        {
            BlockPattern blockPattern = this.getEntityPattern();
            BlockPattern.PatternHelper patternHelper = blockPattern.match(world, pos);

            if (patternHelper != null)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(NODROP, Boolean.valueOf(true)), 2);
                for (int j = 0; j < blockPattern.getPalmLength(); ++j)
                {
                    for (int k = 0; k < blockPattern.getThumbLength(); ++k)
                    {
                        BlockWorldState blockWorldState = patternHelper.translateOffset(j, k, 0);
                        world.setBlockState(blockWorldState.getPos(), Blocks.AIR.getDefaultState(), 2);
                    }
                }

                EntityGerKing entityGerking = new EntityGerKing(world);
                BlockPos pos2 = patternHelper.translateOffset(0, 2, 0).getPos();
                entityGerking.setLocationAndAngles(pos2.getX() + 0.5D, pos2.getY() + 0.55D, pos2.getZ() + 0.5D, patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F, 0.0F);
                entityGerking.renderYawOffset = patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F;
                // 可在此来个ger王的开场动画

                /* for (EntityPlayer entityplayer : worldIn.getEntitiesWithinAABB(EntityPlayer.class, entitygerking.getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D)))
                {
                    entityplayer.triggerAchievement(AchievementLoader.ger);
                }*/

                for (EntityPlayerMP entityPlayerMP : world.getEntitiesWithinAABB(EntityPlayerMP.class, entityGerking.getEntityBoundingBox().grow(50.0D)))
                {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(entityPlayerMP, entityGerking);
                }

                world.spawnEntity(entityGerking);

                for (int l = 0; l < 120; ++l)
                {
                    world.spawnParticle(EnumParticleTypes.SNOWBALL, pos.getX() + world.rand.nextDouble(), pos.getY() - 2.0D + world.rand.nextDouble() * 4.0D, pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                for (int count = 0; count < 6; count++)
                {
                    EntityLightningBolt lightningBolt = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getX(), false);
                    world.spawnEntity(lightningBolt);
                }

                world.setLightFor(EnumSkyBlock.SKY, pos, 15);

                for (int i = 0; i < blockPattern.getPalmLength(); ++i)
                {
                    for (int j = 0; j < blockPattern.getThumbLength(); ++j)
                    {
                        BlockWorldState blockWorldState = patternHelper.translateOffset(i, j, 0);
                        world.notifyNeighborsRespectDebug(blockWorldState.getPos(), Blocks.AIR, false);
                    }
                }
            }
        }
    }
}
