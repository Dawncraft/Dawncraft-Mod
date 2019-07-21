package io.github.dawncraft.container;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class SkillInventoryLearning implements IInventory, ISkillInventory
{
    private final SkillContainer parent;
    private final int inventoryCount;
    private final int skillInventoryCount;
    private final ItemStack[] itemStackList;
    private final SkillStack[] skillStackList;

    public SkillInventoryLearning(SkillContainer container, int itemAmount, int skillAmount)
    {
        this.inventoryCount = itemAmount;
        this.skillInventoryCount = skillAmount;
        this.itemStackList = new ItemStack[itemAmount];
        this.skillStackList = new SkillStack[skillAmount];
        this.parent = container;
    }

    // IWorldNameable

    @Override
    public String getName()
    {
        return "container.learning";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    // Skill Inventory

    @Override
    public int getSkillInventorySize()
    {
        return this.skillStackList.length;
    }

    @Override
    public void setSkillInventorySlot(int index, SkillStack skillStack)
    {
        this.skillStackList[index] = skillStack;
        this.parent.onLearnMatrixChanged(this);
    }

    @Override
    public SkillStack getSkillStackInSlot(int index)
    {
        return index >= this.getSkillInventorySize() ? null : this.skillStackList[index];
    }

    @Override
    public SkillStack removeSkillStackFromSlot(int index)
    {
        if (this.skillStackList[index] != null)
        {
            SkillStack skillStack = this.skillStackList[index];
            this.skillStackList[index] = null;
            return skillStack;
        }
        return null;
    }

    @Override
    public void clearSkillStacks()
    {
        for (int i = 0; i < this.skillStackList.length; ++i)
        {
            this.skillStackList[i] = null;
        }
    }

    // Inventory

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return this.itemStackList.length;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack)
    {
        this.itemStackList[index] = itemStack;
        this.parent.onCraftMatrixChanged(this);
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return index >= 0 && index < this.getSizeInventory() ? this.itemStackList[index] : null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if (this.itemStackList[index] != null)
        {
            if (this.itemStackList[index].getCount() <= count)
            {
                ItemStack itemstack1 = this.itemStackList[index];
                this.itemStackList[index] = null;
                this.parent.onCraftMatrixChanged(this);
                return itemstack1;
            }
            else
            {
                ItemStack itemstack = this.itemStackList[index].splitStack(count);

                if (this.itemStackList[index].getCount() == 0)
                {
                    this.itemStackList[index] = null;
                }

                this.parent.onCraftMatrixChanged(this);
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        if (this.itemStackList[index] != null)
        {
            ItemStack itemstack = this.itemStackList[index];
            this.itemStackList[index] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.itemStackList.length; ++i)
        {
            this.itemStackList[i] = null;
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    // Common

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    public int getInventoryCount()
    {
        return this.inventoryCount;
    }

    public int getSkillInventoryCount()
    {
        return this.skillInventoryCount;
    }
}
