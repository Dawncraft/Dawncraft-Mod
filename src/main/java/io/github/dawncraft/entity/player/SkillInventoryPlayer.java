package io.github.dawncraft.entity.player;

import java.util.concurrent.Callable;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;

public class SkillInventoryPlayer implements ISkillInventory
{
    /** The player whose inventory this is. */
    public EntityPlayer player;
    /** An array of 36 skill stacks indicating the main player inventory (including the visible bar). */
    public SkillStack[] mainInventory = new SkillStack[36];
    /** Stack helds by mouse. */
    private SkillStack skillStack;
    /** Set true whenever the inventory changes. */
    public boolean inventoryChanged;

    public SkillInventoryPlayer(EntityPlayer playerIn)
    {
        this.player = playerIn;
    }
    
    @Override
    public String getName()
    {
        return "container.skills";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public IChatComponent getDisplayName()
    {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.player.isDead ? false : player.getDistanceSqToEntity(this.player) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}
    
    @Override
    public int getSizeInventory()
    {
        return this.mainInventory.length;
    }

    @Override
    public SkillStack getStackInSlot(int index)
    {
        return this.mainInventory[index];
    }

    @Override
    public void setInventorySlotContents(int index, SkillStack stack)
    {
        this.mainInventory[index] = stack;
    }

    @Override
    public SkillStack removeStackFromSlot(int index)
    {
        if (this.mainInventory[index] != null)
        {
            SkillStack skillstack = this.mainInventory[index].copy();
            this.mainInventory[index] = null;
            return skillstack;
        }
        return null;
    }

    @Override
    public boolean isSkillValidForSlot(int index, SkillStack stack)
    {
        return true;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            this.mainInventory[i] = null;
        }
    }

    public boolean addSkillStackToInventory(final SkillStack skillStackIn)
    {
        if (skillStackIn != null && skillStackIn.getSkill() != null)
        {
            try
            {
                int i = this.getFirstEmptyStack();
                
                if (i >= 0)
                {
                    this.mainInventory[i] = SkillStack.copySkillStack(skillStackIn);
                    this.mainInventory[i].animationsToGo = 5;
                    return true;
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding skill to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Skill being added");
                crashreportcategory.addCrashSection("Skill ID", Integer.valueOf(Skill.getIdFromSkill(skillStackIn.getSkill())));
                crashreportcategory.addCrashSection("Skill level", Integer.valueOf(skillStackIn.getSkillLevel()));
                crashreportcategory.addCrashSectionCallable("Item name", new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return skillStackIn.getDisplayName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        return false;
    }

    public int getFirstEmptyStack()
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] == null)
            {
                return i;
            }
        }
        
        return -1;
    }

    public void setSkillStack(SkillStack skillStackIn)
    {
        this.skillStack = skillStackIn;
    }
    
    public SkillStack getSkillStack()
    {
        return this.skillStack;
    }

    @Override
    public void markDirty()
    {
        this.inventoryChanged = true;
    }

    public NBTTagList writeToNBT(NBTTagList nbtTagListIn)
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.mainInventory[i].writeToNBT(nbttagcompound);
                nbtTagListIn.appendTag(nbttagcompound);
            }
        }
        return nbtTagListIn;
    }
    
    public void readFromNBT(NBTTagList nbtTagListIn)
    {
        this.clear();
        
        for (int i = 0; i < nbtTagListIn.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbtTagListIn.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            SkillStack skillstack = SkillStack.loadSkillStackFromNBT(nbttagcompound);
            
            if (skillstack != null)
            {
                if (j >= 0 && j < this.mainInventory.length)
                {
                    this.mainInventory[j] = skillstack;
                }
            }
        }
    }

    public void copyInventory(SkillInventoryPlayer playerInventory)
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            this.mainInventory[i] = SkillStack.copySkillStack(playerInventory.mainInventory[i]);
        }
    }

    public static int getHotbarSize()
    {
        return InventoryPlayer.getHotbarSize();
    }
}
