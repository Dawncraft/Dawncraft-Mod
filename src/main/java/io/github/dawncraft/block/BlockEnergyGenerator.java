package io.github.dawncraft.block;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.tileentity.TileEntityEnergyHeatGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * @author QingChenW
 *
 */
public class BlockEnergyGenerator extends BlockMachine
{
    public BlockEnergyGenerator.EnergyGeneratorType type;

    public BlockEnergyGenerator(EnergyGeneratorType generatorType)
    {
        super();
        this.type = generatorType;
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        switch(this.type)
        {
            default:
            case HEAT: return new TileEntityEnergyHeatGen();
        }
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            int id;
            switch(this.type)
            {
                default:
                case HEAT: id = GuiLoader.GUI_HEAT_GENERATOR; break;
            }
            playerIn.openGui(dawncraft.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if(this.type == EnergyGeneratorType.HEAT)
        {
            IItemHandler slots = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        
            for(int i = slots.getSlots() - 1; i >= 0; --i)
            {
                if (slots.getStackInSlot(i) != null)
                {
                    Block.spawnAsEntity(worldIn, pos, slots.getStackInSlot(i));
                    ((IItemHandlerModifiable) slots).setStackInSlot(i, null);
                }
            }
        }
        
        super.breakBlock(worldIn, pos, state);
    }
    
    /**
     * 0=heat, 1=fluid, 2=solar, 3=wind, 4=nuclear, 5=magic
     * 
     * @author QingChenW
     */
    public enum EnergyGeneratorType
    {
        HEAT(0),
        FLUID(1),
        SOLAR(2),
        WIND(3),
        NUCLEAR(4),
        MAGIC(5);
        
        private int _id;
        
        EnergyGeneratorType(int id)
        {
           this._id = id;
        }
        
        public int getId()
        {  
            return _id;  
        }
    }
}
