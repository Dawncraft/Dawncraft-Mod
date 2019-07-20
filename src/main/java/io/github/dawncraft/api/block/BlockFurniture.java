package io.github.dawncraft.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * The basic furniture block, you can make your own furniture by extending it.
 *
 * @author QingChenW
 */
public abstract class BlockFurniture extends Block
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockFurniture(EnumMaterialType type)
    {
        this(type.material);
        this.setHardness(type.hardness);
        this.setResistance(type.resistance);
        this.setHarvestLevel("hammer", 0);
        this.setSoundType(type.sound);
    }

    public BlockFurniture(Material material)
    {
        super(material);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        return facing;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public enum EnumMaterialType
    {
        WOOD(Material.WOOD, 1.0F, 3.0F, SoundType.WOOD),
        STONE(Material.ROCK, 2.0F, 5.0F, SoundType.STONE),
        IRON(Material.IRON, 5.0F, 10.0F, SoundType.METAL);

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
