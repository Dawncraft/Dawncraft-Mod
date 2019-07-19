package io.github.dawncraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.TextComponentText;
import net.minecraft.util.TextComponentTranslation;
import net.minecraft.util.ITextComponent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;

import java.util.List;

import io.github.dawncraft.skill.SkillStack;

public class SkillInventoryBasic implements ISkillInventory
{
    private String inventoryTitle;
    private int slotsCount;
    private SkillStack[] inventoryContents;
    private List<ISkillInvBasicListener> changeListeners;
    private boolean hasCustomName;

    public SkillInventoryBasic(String title, boolean customName, int slotCount)
    {
        this.inventoryTitle = title;
        this.hasCustomName = customName;
        this.slotsCount = slotCount;
        this.inventoryContents = new SkillStack[slotCount];
    }

    @SideOnly(Side.CLIENT)
    public SkillInventoryBasic(ITextComponent title, int slotCount)
    {
        this(title.getUnformattedText(), true, slotCount);
    }

    @Override
    public String getName()
    {
        return this.inventoryTitle;
    }

    @Override
    public boolean hasCustomName()
    {
        return this.hasCustomName;
    }
    
    public void setCustomName(String title)
    {
        this.hasCustomName = true;
        this.inventoryTitle = title;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentText(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public int getSkillInventorySize()
    {
        return 64;
    }

    @Override
    public void setSkillInventorySlot(int index, SkillStack skillStack)
    {
        this.inventoryContents[index] = skillStack;
        this.markDirty();
    }

    @Override
    public SkillStack getSkillStackInSlot(int index)
    {
        return index >= 0 && index < this.inventoryContents.length ? this.inventoryContents[index] : null;
    }

    public SkillStack addSkillStack(SkillStack stack)
    {
        SkillStack skillStack = stack.copy();
        for (int i = 0; i < this.slotsCount; ++i)
        {
            SkillStack skillStack2 = this.getSkillStackInSlot(i);
            
            if (skillStack2 == null)
            {
                this.setSkillInventorySlot(i, skillStack);
                this.markDirty();
                return null;
            }
        }
        return skillStack;
    }

    @Override
    public SkillStack removeSkillStackFromSlot(int index)
    {
        if (this.inventoryContents[index] != null)
        {
            SkillStack skillStack = this.inventoryContents[index];
            this.inventoryContents[index] = null;
            return skillStack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void clearSkillStacks()
    {
        for (int i = 0; i < this.inventoryContents.length; ++i)
        {
            this.inventoryContents[i] = null;
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public void markDirty()
    {
        if (this.changeListeners != null)
        {
            for (int i = 0; i < this.changeListeners.size(); ++i)
            {
                this.changeListeners.get(i).onSkillInventoryChanged(this);
            }
        }
    }

    public void addInventoryChangeListener(ISkillInvBasicListener listener)
    {
        if (this.changeListeners == null)
        {
            this.changeListeners = Lists.<ISkillInvBasicListener>newArrayList();
        }

        this.changeListeners.add(listener);
    }

    public void removeInventoryChangeListener(ISkillInvBasicListener listener)
    {
        this.changeListeners.remove(listener);
    }
}
