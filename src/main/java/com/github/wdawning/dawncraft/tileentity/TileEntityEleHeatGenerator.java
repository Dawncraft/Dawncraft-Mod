package com.github.wdawning.dawncraft.tileentity;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import com.github.wdawning.dawncraft.block.BlockEleHeatGenerator;
import com.github.wdawning.dawncraft.container.ContainerEleHeatGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
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

public class TileEntityEleHeatGenerator extends TileEntity implements IUpdatePlayerListBox, ISidedInventory
{
    private static final int[] slots = new int[] {0};
	private ItemStack[] eleFurnaceItemStacks = new ItemStack[1];
    private String eleHeatGeneratorCustomName;
    protected int burnTime = 0;
    protected int currentItemBurnTime = 0;
    protected int electric = 0;
    protected final int maxElectric = 12800;

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
        return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack);
	}

	@Override
	public int getField(int id) {
        switch (id)
        {
            case 0:
                return this.burnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.electric;
            case 3:
                return this.maxElectric;
            default:
                return 0;
        }
	}

	@Override
	public void setField(int id, int value) {
        switch (id)
        {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.electric = value;
                break;
        }
	}

	@Override
	public int getFieldCount() {
	        return 4;
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
        return this.hasCustomName() ? this.eleHeatGeneratorCustomName : "container.eleFurnace";
	}

	@Override
	public boolean hasCustomName() {
        return this.eleHeatGeneratorCustomName != null && this.eleHeatGeneratorCustomName.length() > 0;
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

        this.burnTime = compound.getShort("BurnTime");
        this.electric = compound.getShort("Electric");
        this.currentItemBurnTime = getItemBurnTime(this.eleFurnaceItemStacks[0]);

        if (compound.hasKey("CustomName", 8))
        {
            this.eleHeatGeneratorCustomName = compound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("BurnTime", (short)this.burnTime);
        compound.setShort("Electric", (short)this.electric);
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
            compound.setString("CustomName", this.eleHeatGeneratorCustomName);
        }
    }

	@Override
	public IChatComponent getDisplayName() {
        return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getCommandSenderName()) : new ChatComponentTranslation(this.getCommandSenderName(), new Object[0]));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? slots : (side == EnumFacing.UP ? slots : null);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 0)
        {
            Item item = stack.getItem();

            if (item != Items.bucket)
            {
                return false;
            }
        }

        return true;
	}
	
    public String getGuiID()
    {
        return "dawncarft:heat_generator";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerEleHeatGenerator(playerInventory, this);
    }

	@Override
	public void update() {
        boolean flag = this.isBurning();
        boolean isBurning = false;

        if (this.isBurning())
        {
            --this.burnTime;
        }
        
        if(this.electric > 0)
        {
        	--this.electric;
        }

        if (!this.worldObj.isRemote)
        {
                if (!this.isBurning() && this.canSmelt())
                {
                    this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.eleFurnaceItemStacks[0]);

                    if (this.isBurning())
                    {
                        isBurning = true;

                        if (this.eleFurnaceItemStacks[0] != null)
                        {
                            --this.eleFurnaceItemStacks[0].stackSize;

                            if (this.eleFurnaceItemStacks[0].stackSize == 0)
                            {
                                this.eleFurnaceItemStacks[0] = eleFurnaceItemStacks[0].getItem().getContainerItem(eleFurnaceItemStacks[0]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    if (this.electric <= this.maxElectric)
                    {
                        this.electric = this.electric + 3;
                        isBurning = true;
                    }
                }
            }

            if (flag != this.isBurning())
            {
                isBurning = true;
                BlockEleHeatGenerator.setState(this.isBurning(), this.worldObj, this.pos);
            }

        if (isBurning)
        {
            this.markDirty();
        }
	}
	
    public static int getItemBurnTime(ItemStack ItemStack)
    {
        if (ItemStack == null)
        {
            return 0;
        }
        else
        {
            Item item = ItemStack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (block == Blocks.coal_block)
                {
                    return 16000;
                }
            }

            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Items.blaze_rod) return 2400;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(ItemStack);
        }
    }
	
    public static boolean isItemFuel(ItemStack ItemStack)
    {
        return getItemBurnTime(ItemStack) > 0;
    }
    
    public boolean isBurning()
    {
        return this.burnTime > 0;
    }
    
    private boolean canSmelt()
    {
    	if(electric < maxElectric) return true;
		return false;
    }
}
