package io.github.dawncraft.block;

import com.google.common.base.Predicate;

import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.item.ItemInitializer;
import io.github.dawncraft.item.ItemSkullDawn;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockDawnSkull extends BlockSkullDawn
{
    private final static Predicate<BlockWorldState> IS_KING_SKULL = new Predicate<BlockWorldState>()
    {
        @Override
        public boolean apply(BlockWorldState blockworldstate)
        {
            return blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getBlock() == BlockLoader.skull && blockworldstate.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) blockworldstate.getTileEntity()).getSkullType() == 1;
        }
    };
    private BlockPattern kingBasePattern;
    private BlockPattern kingPattern;
    
    @Override
    public ItemSkullDawn getSkullItem()
    {
        return (ItemSkullDawn) ItemInitializer.skull;
    }
    
    @Override
    public void checkEntitySpawn(World world, BlockPos pos, TileEntitySkull tileentity)
    {
        if (tileentity.getSkullType() == 1 && pos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote)
        {
            BlockPattern blockpattern = this.getEntityPattern();
            BlockPattern.PatternHelper patternhelper = blockpattern.match(world, pos);

            if (patternhelper != null)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(NODROP, Boolean.valueOf(true)), 2);
                for (int i = 0; i < blockpattern.getThumbLength(); ++i)
                {
                    BlockWorldState blockworldstate1 = patternhelper.translateOffset(0, i, 0);
                    world.setBlockState(blockworldstate1.getPos(), Blocks.air.getDefaultState(), 2);
                }

                EntityGerKing entitygerking = new EntityGerKing(world);
                BlockPos blockpos = patternhelper.translateOffset(0, 2, 0).getPos();
                entitygerking.setLocationAndAngles((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.55D, (double)blockpos.getZ() + 0.5D, patternhelper.getFinger().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F, 0.0F);
                entitygerking.renderYawOffset = patternhelper.getFinger().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F;
                // 可在此来个ger王的开场动画

                /*                    for (EntityPlayer entityplayer : worldIn.getEntitiesWithinAABB(EntityPlayer.class, entitygerking.getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D)))
                {
                    entityplayer.triggerAchievement(AchievementLoader.ger);
                }*/
                
                world.spawnEntityInWorld(entitygerking);

                for (int l = 0; l < 120; ++l)
                {
                    world.spawnParticle(EnumParticleTypes.SNOWBALL, (double)pos.getX() + world.rand.nextDouble(), (double)(pos.getY() - 2.0D) + world.rand.nextDouble() * 4.0D, (double)pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
                }
                
                for (int count = 0; count < 6; count++)
                {
                    EntityLightningBolt lightningBolt = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getX());
                    world.spawnEntityInWorld(lightningBolt);
                }
                
                world.setLightFor(EnumSkyBlock.SKY, pos, 15);

                for (int j = 0; j < blockpattern.getThumbLength(); ++j)
                {
                    BlockWorldState blockworldstate2 = patternhelper.translateOffset(0, j, 0);
                    world.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.air);
                }
            }
        }
    }

    @Override
    protected BlockPattern getEntityBasePattern()
    {
        if (this.kingBasePattern == null)
        {
            this.kingBasePattern = FactoryBlockPattern.start().aisle(" ", "#", "*").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.diamond_block))).where('*', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.gold_block))).build();
        }
        
        return this.kingBasePattern;
    }

    @Override
    protected BlockPattern getEntityPattern()
    {
        if (this.kingPattern == null)
        {
            this.kingPattern = FactoryBlockPattern.start().aisle("^", "#", "*").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.diamond_block))).where('*', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.gold_block))).where('^', this.IS_KING_SKULL).build();
        }
        
        return this.kingPattern;
    }
}
