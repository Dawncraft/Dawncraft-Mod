package io.github.dawncraft.block.base;

import java.util.Random;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Ore block.
 * <br>Magnet Ore and others.</br>
 * <br>由于创建实例时方块还未初始化，掉落物和方块物品无法使用，请先注册后再调用setItemDropped和setExpDropped设置掉落物（支持链式调用）</br>
 * @author QingChenW
 */
public class BlockOreBase extends Block
{
	private Item droppedItem;
	private int droppedCount;
	private int droppedRange;
	private int droppedBonus;
	private int droppedExpMin;
	private int droppedExpMax;
	
	public BlockOreBase()
	{
		this(Material.rock.getMaterialMapColor());
	}

    public BlockOreBase(MapColor color)
    {
        super(Material.rock, color);
		this.droppedCount = 1;
		this.droppedRange = 0;
		this.droppedBonus = 0;
		this.droppedExpMin = 0;
		this.droppedExpMax = 0;
        this.setHardness(3.0f);
        this.setResistance(5.0f);
    }
    
    public BlockOreBase setItemDropped(Block block)
    {
		return this.setItemDropped(Item.getItemFromBlock(block), 1, 0, 0);
	}
    
    public BlockOreBase setItemDropped(Item item, int count, int range, int bonus)
    {
    	this.droppedItem = item;
		this.droppedCount = count;
		this.droppedRange = range;
		this.droppedBonus = bonus;
		return this;
	}
    
    public Block setExpDropped(int expMin, int expMax)
    {
		this.droppedExpMin = expMin;
		this.droppedExpMax = expMax;
		return this;
    }
    
    public boolean isDroppedItself()
    {
		return this.droppedItem == Item.getItemFromBlock(this);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.isDroppedItself() ? Item.getItemFromBlock(this) : this.droppedItem;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return this.isDroppedItself() ? this.droppedCount : this.droppedCount + random.nextInt(this.droppedRange);
    }
    
    /** 这是比较简单的，不过我实在是不知道该怎么实现，如果你需要的话就用匿名内部类重写此方法
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (!this.isDroppedItself() && fortune > 0)
        {
        	int count = random.nextInt(fortune + this.droppedBonus);
        	if (count < 0) count = 0;
            return this.quantityDropped(random) + count;
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        	return MathHelper.getRandomIntegerInRange(rand, droppedExpMin, droppedExpMax);
        return 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
}
