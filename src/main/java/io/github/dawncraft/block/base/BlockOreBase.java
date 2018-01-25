package io.github.dawncraft.block.base;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Ore block.
 * <br>Magnet Ore and others.</br>
 * <br>
 * harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
 * dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int forture)
 * dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float 1.0F, int fortune)
 * getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
 *</br>
 * @author QingChenW
 */
public class BlockOreBase extends Block
{
    private int droppedCount;
    private int droppedRange;
    private int droppedExpMin;
    private int droppedExpMax;

    public BlockOreBase()
    {
        this(Material.rock.getMaterialMapColor());
    }
    
    public BlockOreBase(MapColor color)
    {
        this(color, 1, 0, 0, 0);
    }
    
    public BlockOreBase(int count, int range, int expMin, int expMax)
    {
        this(Material.rock.getMaterialMapColor(), count, range, expMin, expMax);
    }
    
    public BlockOreBase(MapColor color, int count, int range, int expMin, int expMax)
    {
        super(Material.rock, color);
        this.droppedCount = count;
        this.droppedRange = range;
        this.droppedExpMin = expMin;
        this.droppedExpMax = expMax;
        this.setHardness(3.0f);
        this.setResistance(5.0f);
    }

    public Item getMineral()
    {
        return Item.getItemFromBlock(this);
    }
    
    public boolean isDroppedItself()
    {
        return this.getMineral() == Item.getItemFromBlock(this);
    }
    
    public boolean isDroppedItself(IBlockState state, Random rand, int fortune)
    {
        return this.isDroppedItself();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.isDroppedItself(state, rand, fortune) ? Item.getItemFromBlock(this) : this.getMineral();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random rand)
    {
        if (!this.isDroppedItself(state, rand, fortune) && fortune > 0)
        {
            return this.quantityDroppedWithBonus(fortune, rand);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }

    /** 需要的话请用匿名内部类重写此方法
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return fortune;
    }
    
    @Override
    public int quantityDropped(Random random)
    {
        return this.isDroppedItself() ? this.droppedCount : this.droppedCount + random.nextInt(this.droppedRange);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.isDroppedItself(state, rand, fortune)) return 0;
        return MathHelper.getRandomIntegerInRange(rand, this.droppedExpMin, this.droppedExpMax);
    }
}
