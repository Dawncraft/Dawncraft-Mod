package WdawningStudio.DawnW.science.block;

import java.util.Random;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockComputer extends Block
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockComputer(Material computerType)
	{
		super(computerType);
	}

    //Simple computer case
    public static class SimpleComputer extends BlockComputer
    {
    	public SimpleComputer()
    	{
    		super(Material.iron);
    		this.setUnlocalizedName("simpleComputer");
    		this.setHarvestLevel("ItemPickaxe", 1);
    		this.setHardness(3.0f);
        	this.setResistance(3.0f);
        	this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        	this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }

    //High computer case
    public static class HighComputer extends BlockComputer
    {
    	public HighComputer()
    	{
    		super(Material.iron);
    		this.setUnlocalizedName("highComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
    		this.setHardness(3.0f);
    		this.setResistance(3.0f);
    		this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }

    //Pro computer case 
    public static class ProComputer extends BlockComputer
    {
    	public ProComputer()
    	{
    		super(Material.iron);
    		this.setUnlocalizedName("proComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
    		this.setHardness(3.0f);
    		this.setResistance(3.0f);
    		this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }

    //Super computer case
    public static class SuperComputer extends BlockComputer
    {
    	public SuperComputer()
    	{
    		super(Material.iron);
    		this.setUnlocalizedName("superComputer");
    		this.setHarvestLevel("ItemPickaxe", 2);
    		this.setHardness(3.0f);
    		this.setResistance(3.0f);
    		this.setStepSound(soundTypePiston);
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
    		this.setCreativeTab(CreativeTabsLoader.tabComputer);
    	}
    }
    
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}
    
	//方块状态太难弄了,我不玩♂你♀行了吧.........
	//其实是从家具mod里抄的,太无耻!!!!!!!!
	/*
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		return state.withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { FACING });
	}
	*/
}