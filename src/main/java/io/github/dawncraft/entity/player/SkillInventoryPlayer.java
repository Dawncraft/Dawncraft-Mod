package io.github.dawncraft.entity.player;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ReportedException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class SkillInventoryPlayer implements ISkillInventory
{
    /** The player whose inventory this is. */
    public EntityPlayer player;
    /** An array of 36 skill stacks indicating the main player inventory (including the visible bar). */
    public SkillStack[] inventory = new SkillStack[36];
    /** Stack held by mouse. */
    private SkillStack skillStack;
    /** Set true whenever the inventory changes. */
    public boolean inventoryChanged;

    public SkillInventoryPlayer(EntityPlayer player)
    {
        this.player = player;
    }

    @Override
    public String getName()
    {
        return "container.skillInventory";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
    }

    @Override
    public int getSkillInventorySize()
    {
        return this.inventory.length;
    }

    @Override
    public SkillStack getSkillStackInSlot(int index)
    {
        return this.inventory[index];
    }

    @Override
    public void setSkillInventorySlot(int index, SkillStack stack)
    {
        this.inventory[index] = stack;
    }

    @Override
    public SkillStack removeSkillStackFromSlot(int index)
    {
        if (this.inventory[index] != null)
        {
            SkillStack stack = this.inventory[index];
            this.inventory[index] = null;
            return stack;
        }
        return null;
    }

    @Override
    public void clearSkillStacks()
    {
        for (int i = 0; i < this.inventory.length; ++i)
        {
            this.inventory[i] = null;
        }
    }

    public int getFirstEmptyStack()
    {
        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] == null)
            {
                return i;
            }
        }

        return -1;
    }

    public boolean addSkillStackToInventory(final SkillStack skillStack)
    {
        if (skillStack != null && skillStack.getSkill() != null)
        {
            try
            {
                int i = this.getFirstEmptyStack();

                if (i >= 0)
                {
                    this.inventory[i] = SkillStack.copySkillStack(skillStack);
                    this.inventory[i].animationsToGo = 5;
                    return true;
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding skill to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Skill being added");
                crashreportcategory.addCrashSection("Skill ID", Integer.valueOf(Skill.getIdFromSkill(skillStack.getSkill())));
                crashreportcategory.addCrashSection("Skill level", Integer.valueOf(skillStack.getSkillLevel()));
                crashreportcategory.addDetail("Skill name", new ICrashReportDetail<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return skillStack.getDisplayName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        return false;
    }

    /**
     * Removes matching skills from the inventory.
     * <br>Only count when "count" is 0.</br>
     *
     * @param skill The skill to match, null ignores.
     * @param level The level of the skill to match, 0 ignores.
     * @param count The number of skills to remove. If less than 0, removes all matching items.
     * @param skillNBT The NBT data to match, null ignores.
     * @return The number of skills removed from the inventory.
     */
    public int clearMatchingSkills(Skill skill, int level, int count, NBTTagCompound skillNBT)
    {
        int removed = 0;

        for (int i = 0; i < this.inventory.length; ++i)
        {
            SkillStack stack = this.inventory[i];

            if (stack != null && (skill == null || stack.getSkill() == skill) && (level < 1 || stack.skillLevel == level) && (skillNBT == null || NBTUtil.areNBTEquals(skillNBT, stack.getTagCompound(), true)))
            {
                ++removed;

                if (count != 0)
                {
                    this.inventory[i] = null;

                    if (count > 0 && removed >= count)
                    {
                        return removed;
                    }
                }
            }
        }

        if (this.skillStack != null && (skill == null || this.skillStack.getSkill() == skill) && (level < 1 || this.skillStack.skillLevel == level) && (skillNBT == null || NBTUtil.areNBTEquals(skillNBT, this.skillStack.getTagCompound(), true)))
        {
            ++removed;

            if (count != 0)
            {
                this.skillStack = null;

                if (count > 0 && removed >= count)
                {
                    return removed;
                }
            }
        }

        return removed;
    }

    public void setSkillStack(SkillStack skillStack)
    {
        this.skillStack = skillStack;
    }

    public SkillStack getSkillStack()
    {
        return this.skillStack;
    }

    public void decrementAnimations()
    {
        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                this.inventory[i].updateAnimation(this.player.world, this.player, i);
            }
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.player.isDead ? false : player.getDistance(this.player) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public void markDirty()
    {
        this.inventoryChanged = true;
    }

    public NBTTagList writeToNBT(NBTTagList nbtTagListIn)
    {
        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound);
                nbtTagListIn.appendTag(nbttagcompound);
            }
        }
        return nbtTagListIn;
    }

    public void readFromNBT(NBTTagList nbtTagListIn)
    {
        this.clearSkillStacks();

        for (int i = 0; i < nbtTagListIn.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbtTagListIn.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            SkillStack skillstack = SkillStack.loadSkillStackFromNBT(nbttagcompound);

            if (skillstack != null)
            {
                if (j >= 0 && j < this.inventory.length)
                {
                    this.inventory[j] = skillstack;
                }
            }
        }
    }

    public void copyInventory(SkillInventoryPlayer playerInventory)
    {
        for (int i = 0; i < this.inventory.length; ++i)
        {
            this.inventory[i] = SkillStack.copySkillStack(playerInventory.inventory[i]);
        }
    }

    public static int getHotbarSize()
    {
        return InventoryPlayer.getHotbarSize();
    }
}
