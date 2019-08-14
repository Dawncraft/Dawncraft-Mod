package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.container.DawnGuiHandler;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.stats.StatInit;
import io.github.dawncraft.tileentity.TileEntityEnergyGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Energy generator
 *
 * @author QingChenW
 */
public class BlockEnergyGenerator extends BlockMachine
{
    public EnumGeneratorType type;

    public BlockEnergyGenerator(EnumGeneratorType generatorType)
    {
        super();
        this.type = generatorType;
        this.setCreativeTab(CreativeTabsLoader.ENERGY);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityEnergyGenerator();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            int id;
            switch (this.type)
            {
            default:
            case HEAT:
                id = DawnGuiHandler.GUI_HEAT_GENERATOR;
                break;
            }
            player.openGui(Dawncraft.instance, id, world, pos.getX(), pos.getY(), pos.getZ());
            player.addStat(StatInit.MACHINE_INTERACTION);
        }
        return true;
    }

    /**
     * heat, fluid, solar, wind, nuclear, magic
     *
     * @author QingChenW
     */
    public enum EnumGeneratorType
    {
        HEAT, FLUID, SOLAR, WIND, NUCLEAR, MAGIC;
    }
}
