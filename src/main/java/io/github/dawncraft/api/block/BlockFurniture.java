package io.github.dawncraft.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The basic furniture block, you can make your own furniture by extending it.
 *
 * @author QingChenW
 */
public abstract class BlockFurniture extends Block
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockFurniture(EnumMaterialType type)
    {
        this(type.material);
        this.setHardness(type.hardness);
        this.setResistance(type.resistance);
        this.setHarvestLevel("hammer", 0);
        this.setStepSound(type.sound);
    }

    public BlockFurniture(Material material)
    {
        super(material);
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

    @Override
    public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return false;
    }
    
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        return facing;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public enum EnumMaterialType
    {
        WOOD(Material.wood, 1.0F, 3.0F, Block.soundTypeWood),
        STONE(Material.rock, 2.0F, 5.0F, Block.soundTypeStone),
        IRON(Material.iron, 5.0F, 10.0F, Block.soundTypeMetal);

        private Material material;
        private float hardness;
        private float resistance;
        private SoundType sound;
        
        private EnumMaterialType(Material material, float hardness, float resistance, SoundType sound)
        {
            this.material = material;
            this.hardness = hardness;
            this.resistance = resistance;
            this.sound = sound;
        }
        
        public Material getMaterial()
        {
            return this.material;
        }
        
        public float getHardness()
        {
            return this.hardness;
        }
        
        public float getResistance()
        {
            return this.resistance;
        }
        
        public SoundType getSound()
        {
            return this.sound;
        }
    }
}
