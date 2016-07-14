package com.github.wdawning.dawncraft.tileentity;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import com.github.wdawning.dawncraft.block.BlockMachineEleFurnace;
import com.github.wdawning.dawncraft.gui.ContainerMachineEleFurnace;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityMachineEleFurnace extends TileEntity implements IUpdatePlayerListBox, ISidedInventory
{
    private static final int[] slotsLeft = new int[] {0};
    private static final int[] slotsRight = new int[] {1};
	private ItemStack[] eleFurnaceItemStacks = new ItemStack[2];
    private String eleFurnaceCustomName;
    private boolean isBurning = false;
    private int cookTime = 0;
    private int totalCookTime = 0;

	@Override
	public int getSizeInventory() {
		return this.eleFurnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.eleFurnaceItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.eleFurnaceItemStacks[index] != null)
		{
			ItemStack itemstack;

			if (this.eleFurnaceItemStacks[index].stackSize <= count)
			{
				itemstack = this.eleFurnaceItemStacks[index];
				this.eleFurnaceItemStacks[index] = null;
				return itemstack;
			}
            else
            {
            	itemstack = this.eleFurnaceItemStacks[index].splitStack(count);
            	
            	if (this.eleFurnaceItemStacks[index].stackSize == 0)
            	{
            		this.eleFurnaceItemStacks[index] = null;
            	}

            	return itemstack;
            }
		}
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
        if (this.eleFurnaceItemStacks[index] != null)
        {
            ItemStack itemstack = this.eleFurnaceItemStacks[index];
            this.eleFurnaceItemStacks[index] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        this.eleFurnaceItemStacks[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
	}

	@Override
	public int getInventoryStackLimit() {
        return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index == 1 ? false : (index != 1 ? true : false);
	}

	@Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.cookTime;
            case 1:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

	@Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.cookTime = value;
                break;
            case 1:
                this.totalCookTime = value;
        }
    }

	@Override
	public int getFieldCount() {
	        return 2;
	}

	@Override
	public void clear() {
        for (int i = 0; i < this.eleFurnaceItemStacks.length; ++i)
        {
            this.eleFurnaceItemStacks[i] = null;
        }
	}
	
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
	
	@Override
	public String getCommandSenderName() {
        return this.hasCustomName() ? this.eleFurnaceCustomName : "container.MachineEleFurnace";
	}

	@Override
	public boolean hasCustomName() {
        return this.eleFurnaceCustomName != null && this.eleFurnaceCustomName.length() > 0;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.eleFurnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.eleFurnaceItemStacks.length)
            {
                this.eleFurnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.cookTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");

        if (compound.hasKey("CustomName", 8))
        {
            this.eleFurnaceCustomName = compound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("CookTime", (short)this.cookTime);
        compound.setShort("CookTimeTotal", (short)this.totalCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.eleFurnaceItemStacks.length; ++i)
        {
            if (this.eleFurnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.eleFurnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        compound.setTag("Items", nbttaglist);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.eleFurnaceCustomName);
        }
    }

	@Override
	public IChatComponent getDisplayName() {
        return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getCommandSenderName()) : new ChatComponentTranslation(this.getCommandSenderName(), new Object[0]));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsRight : slotsLeft;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
	}
	
    public int getGuiID()
    {
        return 65;
    }

	@Override
	public void update() {
        boolean flag = this.isBurning();
        boolean hasElectric = true;//Test...
/*        TileEntityEleHeatGenerator eleHeatGenerator = TileEleHeatGenerator;
        boolean hasElectric = eleHeatGenerator.getField(2) > 0 ? true : false;

        if (this.isBurning())
        {
            eleHeatGenerator.setField(2, eleHeatGenerator.getField(2) - 1);
        }*/
        
        if (!this.worldObj.isRemote)
        {
            if (!this.isBurning() && !hasElectric)
            {
                if (!this.isBurning() && this.cookTime > 0)
                {
                    this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
                }
            }
            else
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    if (hasElectric)
                    {
                    	this.isBurning = true;
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.eleFurnaceItemStacks[0]);
                        this.smeltItem();
                        this.isBurning = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                    this.isBurning = false;
                }
            }

            if (flag != this.isBurning())
            {
                BlockMachineEleFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if (this.isBurning)
        {
            this.markDirty();
        }
	}
	
    public boolean isBurning()
    {
        return this.isBurning;
    }
    
    public int getCookTime(ItemStack stack)
    {
        return 200;
    }
    
    private boolean canSmelt()
    {
        if (this.eleFurnaceItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.eleFurnaceItemStacks[0]);
            if (itemstack == null) return false;
            if (this.eleFurnaceItemStacks[1] == null) return true;
            if (!this.eleFurnaceItemStacks[1].isItemEqual(itemstack)) return false;
            int result = eleFurnaceItemStacks[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.eleFurnaceItemStacks[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }
    
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.eleFurnaceItemStacks[0]);

            if (this.eleFurnaceItemStacks[1] == null)
            {
                this.eleFurnaceItemStacks[1] = itemstack.copy();
            }
            else if (this.eleFurnaceItemStacks[1].getItem() == itemstack.getItem())
            {
                this.eleFurnaceItemStacks[1].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.eleFurnaceItemStacks[0].stackSize;

            if (this.eleFurnaceItemStacks[0].stackSize <= 0)
            {
                this.eleFurnaceItemStacks[0] = null;
            }
        }
    }
}
