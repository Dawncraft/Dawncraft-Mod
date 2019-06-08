package io.github.dawncraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import io.github.dawncraft.skill.SkillStack;

public class SkillInventoryLearnResult implements ISkillInventory
{
    /** An item containing the result of the learning formula */
    private SkillStack stackResult;

    @Override
    public String getName()
    {
        return "container.learning.result";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public IChatComponent getDisplayName()
    {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
    }

    @Override
    public int getSkillInventorySize()
    {
        return 1;
    }

    @Override
    public void setSkillInventorySlot(int index, SkillStack skillStack)
    {
        this.stackResult = skillStack;
    }

    @Override
    public SkillStack getSkillStackInSlot(int index)
    {
        return this.stackResult;
    }

    @Override
    public SkillStack removeSkillStackFromSlot(int index)
    {
        if (this.stackResult != null)
        {
            SkillStack skillStack = this.stackResult;
            this.stackResult = null;
            return skillStack;
        }
        return null;
    }

    @Override
    public void clearSkillStacks()
    {
        this.stackResult = null;
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
    public void markDirty() {}
}
