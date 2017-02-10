package io.github.dawncraft.block;

import java.util.Random;

import io.github.dawncraft.common.CreativeTabsLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Ore block.
 * <br>Magnet Ore and others.</br>
 * 
 * @author QingChenW
 */
public class BlockOre extends Block
{
    public BlockOre(MapColor color)
    {
        super(Material.rock, color);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypePiston);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 1;
    }
    
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        {
            int i = 0;

            if (this == Blocks.coal_ore)
            {
                i = MathHelper.getRandomIntegerInRange(rand, 0, 2);
            }

            return i;
        }
        return 0;
    }
}
