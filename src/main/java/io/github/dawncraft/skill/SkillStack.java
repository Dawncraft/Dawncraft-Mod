package io.github.dawncraft.skill;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.stats.StatLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkillStack
{
    private Skill skill;
    public int cooldown;
    public int skillLevel;
    public int animationsToGo;
    
    public SkillStack(Skill skill)
    {
        this(skill, 0);
    }
    
    public SkillStack(Skill skill, int level)
    {
        this(skill, level, 0);
    }
    
    public SkillStack(Skill skill, int level, int cd)
    {
        this.skill = skill;
        this.cooldown = cd;
        this.skillLevel = level;
        if (this.skillLevel < 0) this.skillLevel = 0;
        if (this.skillLevel > skill.getMaxLevel()) this.skillLevel = skill.getMaxLevel();
    }
    
    private SkillStack() {}

    public static SkillStack loadSkillStackFromNBT(NBTTagCompound nbt)
    {
        SkillStack skillstack = new SkillStack();
        skillstack.readFromNBT(nbt);
        return skillstack.getSkill() != null ? skillstack : null;
    }

    public void setSkill(Skill newSkill)
    {
        this.skill = newSkill;
    }
    
    public Skill getSkill()
    {
        return this.skill;
    }
    
    public float getSkillConsume()
    {
        return this.getSkill().getConsume(this);
    }

    public int getTotalPrepare()
    {
        return this.getSkill().getTotalPrepare(this);
    }

    public int getMaxDuration()
    {
        return this.getSkill().getMaxDuration(this);
    }
    
    public int getCooldown()
    {
        return this.getSkill().getCooldown(this);
    }

    public int getTotalCooldown()
    {
        return this.getSkill().getTotalCooldown(this);
    }
    
    public int getSkillLevel()
    {
        return this.getSkill().getLevel(this);
    }
    
    public void setSkillLevel(int level)
    {
        this.getSkill().setLevel(this, level);
    }

    public int getMaxLevel()
    {
        return this.getSkill().getMaxLevel();
    }

    public String getUnlocalizedName()
    {
        return this.skill.getUnlocalizedName(this);
    }

    public String getDisplayName()
    {
        return this.getSkill().getSkillStackDisplayName(this);
    }

    public IChatComponent getChatComponent()
    {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.getDisplayName());
        IChatComponent ichatcomponent = new ChatComponentText("[").appendSibling(chatcomponenttext).appendText("]");

        if (this.skill != null)
        {
            String information = "";

            for(String s : this.getTooltip(null, Minecraft.getMinecraft().gameSettings.advancedItemTooltips))
                information = information + s + "\n";

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            this.writeToNBT(nbttagcompound);
            information += nbttagcompound.toString();

            ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(information)));
            ichatcomponent.getChatStyle().setColor(EnumChatFormatting.AQUA);
        }

        return ichatcomponent;
    }
    
    @SideOnly(Side.CLIENT)
    public List<String> getTooltip(EntityPlayer playerIn, boolean advanced)
    {
        List<String> list = Lists.<String>newArrayList();
        
        String s = this.getDisplayName();
        if (advanced)
        {
            String s1 = "";

            if (s.length() > 0)
            {
                s = s + " (";
                s1 = ")";
            }

            int id = Skill.getIdFromSkill(this.skill);
            s = s + String.format("#%04d", Integer.valueOf(id)) + s1;
        }
        list.add(s);

        this.skill.addInformation(this, playerIn, list, advanced);

        list.add(StatCollector.translateToLocal("skill.level") + this.getSkillLevel());
        list.add(StatCollector.translateToLocal("skill.consume") + this.getSkillConsume());

        DawnEventFactory.onItemTooltip(this, playerIn, list, advanced);
        return list;
    }
    
    public void updateAnimation(World worldIn, Entity entityIn, int skillSlot)
    {
        if (this.animationsToGo > 0)
        {
            --this.animationsToGo;
        }
        
        this.skill.onUpdate(this, worldIn, entityIn, skillSlot);
    }
    
    public boolean onSkillSpell(EntityPlayer playerIn, World worldIn)
    {
        //if (!worldIn.isRemote) return DawnEventFactory.onSpellSkillIntoWorld(this, playerIn, worldIn);
        boolean flag = this.getSkill().onSkillSpell(this, playerIn, worldIn);

        if (flag)
        {
            playerIn.triggerAchievement(StatLoader.objectSpellStats[Skill.getIdFromSkill(this.skill)]);
        }

        return flag;
    }
    
    public EnumSpellResult onSkillSpelling(World worldIn, EntityPlayer playerIn)
    {
        return this.getSkill().onSkillSpelling(this, worldIn, playerIn);
    }

    public SkillStack onSkillSpellFinish(World worldIn, EntityPlayer playerIn)
    {
        return this.getSkill().onSkillSpellFinish(this, worldIn, playerIn);
    }
    
    public void onPlayerStoppedSpelling(World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        this.getSkill().onPlayerStoppedSpelling(this, worldIn, playerIn, timeLeft);
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        ResourceLocation resourcelocation = (ResourceLocation) Skill.skillRegistry.getNameForObject(this.skill);
        nbt.setString("id", resourcelocation == null ? "minecraft:null" : resourcelocation.toString());
        nbt.setShort("Level", (short) this.skillLevel);
        return nbt;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        if (nbt.hasKey("id", 8))
        {
            this.setSkill(Skill.getByNameOrId(nbt.getString("id")));
        }
        else
        {
            this.setSkill(Skill.getSkillById(nbt.getShort("id")));
        }
        this.skillLevel = nbt.getShort("Level");
        if (this.skillLevel < 0) this.skillLevel = 0;
        if (this.skillLevel > this.skill.getMaxLevel()) this.skillLevel = this.skill.getMaxLevel();
    }
    
    public static SkillStack copySkillStack(SkillStack stack)
    {
        return stack == null ? null : stack.copy();
    }
    
    public SkillStack copy()
    {
        SkillStack skillstack = new SkillStack(this.skill, this.skillLevel);
        return skillstack;
    }
    
    public static boolean areSkillStacksEqual(SkillStack stackA, SkillStack stackB)
    {
        return stackA == null && stackB == null ? true : stackA != null && stackB != null ? stackA.isSkillStackEqual(stackB) : false;
    }

    private boolean isSkillStackEqual(SkillStack other)
    {
        return this.skill == other.skill ? this.skillLevel == other.skillLevel ? true : false : false;
    }

    public static boolean areSkillsEqual(SkillStack stackA, SkillStack stackB)
    {
        return stackA == null && stackB == null ? true : stackA != null && stackB != null ? stackA.isSkillEqual(stackB) : false;
    }

    public boolean isSkillEqual(SkillStack other)
    {
        return other != null && this.skill == other.skill;
    }

    @Override
    public String toString()
    {
        return this.getUnlocalizedName() + "@" + this.skillLevel;
    }
}
