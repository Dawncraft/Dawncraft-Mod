package io.github.dawncraft.tileentity;

import io.github.dawncraft.block.BlockEnergyGenerator;
import io.github.dawncraft.block.BlockEnergyGenerator.EnergyGeneratorType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 *
 * @author QingChenW
 */
public class TileEntityEnergyGenerator extends TileEntity implements ITickable
{
    public BlockEnergyGenerator.EnergyGeneratorType generatorType;
    public ItemStackHandler fuelItemStack = new ItemStackHandler();
    public int generatorBurnTime;
    public int currentItemBurnTime;
    public int electricity;
    public final int Max_Electricity = 12800;

    public TileEntityEnergyGenerator()
    {
        super();
    }

    public TileEntityEnergyGenerator(EnergyGeneratorType type)
    {
        super();
        this.generatorType = type;
    }
    
    public IChatComponent getDisplayName()
    {
        String name;
        switch(this.generatorType)
        {
            default:
            case HEAT: name = "container.heatGenerator"; break;
        }
        return new ChatComponentTranslation(name, new Object[0]);
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            T result = (T) this.fuelItemStack;
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fuelItemStack.deserializeNBT(compound.getCompoundTag("FuelInventory"));
        this.generatorBurnTime = compound.getShort("BurnTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.fuelItemStack.getStackInSlot(0));
        this.electricity = compound.getShort("Electricity");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("FuelInventory", this.fuelItemStack.serializeNBT());
        compound.setShort("BurnTime", (short) this.generatorBurnTime);
        compound.setShort("Electricity", (short) this.electricity);
    }
    
    // 需替代方案
    /*    @Override
    public void onLoad()
    {
        Block block = this.getWorld().getBlockState(this.getPos()).getBlock();
        if(block instanceof BlockEnergyGenerator)
        {
            this.generatorType = ((BlockEnergyGenerator) block).generatorType;
        }
    }*/

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void update()
    {
        boolean wasWorking = this.isWorking();

        if(wasWorking) --this.generatorBurnTime;
        
        if (!this.worldObj.isRemote)
        {
            if(!this.isWorking() && this.canWork())
            {
                this.currentItemBurnTime = this.generatorBurnTime = TileEntityFurnace.getItemBurnTime(this.fuelItemStack.getStackInSlot(0));

                if(this.isWorking())
                {
                    this.markDirty();
                    this.fuelItemStack.extractItem(0, 1, false);
                }
            }
            
            if(this.isWorking() && this.canWork())
            {
                ++this.electricity;
            }

            if (wasWorking != this.isWorking())
            {
                this.markDirty();
                BlockEnergyGenerator.setBlockState(this.isWorking(), this.worldObj, this.pos);
            }
        }
    }

    public boolean isWorking()
    {
        return this.generatorBurnTime > 0;
    }

    private boolean canWork()
    {
        return this.electricity < this.Max_Electricity;
    }
}
