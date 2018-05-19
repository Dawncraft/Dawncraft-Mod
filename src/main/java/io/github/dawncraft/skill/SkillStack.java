package io.github.dawncraft.skill;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayer;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.entity.player.PlayerUtils;
import io.github.dawncraft.stats.StatLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
    public int skillLevel;
    public int animationsToGo;
    
    public SkillStack(Skill skill)
    {
        this(skill, 1);
    }
    
    public SkillStack(Skill skill, int level)
    {
        this.skill = skill;
        this.skillLevel = level;
        if (this.skillLevel < 1) this.skillLevel = 1;
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
        return this.getSkill().getPrepare(this) + Skill.getPublicPrepare();
    }

    public int getMaxDuration()
    {
        return this.getSkill().getMaxDuration(this);
    }

    public int getTotalCooldown()
    {
        return this.getSkill().getCooldown(this);
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
        return this.getSkill().getUnlocalizedName(this);
    }

    public String getDisplayName()
    {
        return this.getSkill().getSkillStackDisplayName(this);
    }
    
    public String getDisplayDesc()
    {
        return this.getSkill().getSkillStackDisplayDesc(this);
    }

    // 这什么玩意,其实我也不打算实现技能的自定义NBT啦
    // 不过考虑到可能需要,例如击杀1个实体某个数值加一,到100技能升级或者变化之类的是需要NBT的
    // 所以 TODO 技能自定义NBT
    public boolean hasTagCompound()
    {
        return true;
    }

    // 只会在发送聊天信息时发送一次,所以按键显示额外信息毫无卵用
    // 所以要想尽办法改一下啥子的
    public IChatComponent getChatComponent()
    {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.getDisplayName());
        IChatComponent ichatcomponent = new ChatComponentText("[").appendSibling(chatcomponenttext).appendText("]");

        if (this.getSkill() != null)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(tagCompound.toString())));
            ichatcomponent.getChatStyle().setColor(EnumChatFormatting.AQUA);
        }

        return ichatcomponent;
    }
    
    @SideOnly(Side.CLIENT)
    public List<String> getTooltip(EntityPlayer playerIn, boolean advanced)
    {
        List<String> list = Lists.<String>newArrayList();
        
        String name = this.getDisplayName();
        String suffix = "";
        if (advanced)
        {
            suffix = String.format("#%04d", Skill.getIdFromSkill(this.skill));
            if (name.length() > 0)
            {
                suffix = " (" + suffix + ")";
            }
        }
        list.add(name + suffix);

        list.add(StatCollector.translateToLocalFormatted("skill.level", this.getSkillLevel(), this.getMaxLevel()));
        list.add(this.getDisplayDesc());
        this.skill.addInformation(this, playerIn, list, advanced);

        if(this.getSkillLevel() < this.getMaxLevel())
        {
            if (Keyboard.isKeyDown(KeyLoader.use.getKeyCode()))
            {
                list.add(StatCollector.translateToLocal("skill.nextLevel"));
                SkillStack skillStack = this.copy();
                skillStack.skillLevel++;
                list.add(skillStack.getDisplayDesc());
            }
            else
            {
                list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("skill.moreInfo", Keyboard.getKeyName(KeyLoader.use.getKeyCode())));
            }
        }
        else
        {
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("skill.maxLevel", Keyboard.getKeyName(KeyLoader.use.getKeyCode())));
        }

        if (advanced)
        {
            list.add(EnumChatFormatting.DARK_GRAY + ((ResourceLocation)Skill.skillRegistry.getNameForObject(this.getSkill())).toString());
            if(this.hasTagCompound()) list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocalFormatted("skill.nbtTags", 0));
        }

        return list;
    }
    
    public void updateAnimation(World world, Entity entity, int skillSlot)
    {
        if (this.animationsToGo > 0)
        {
            --this.animationsToGo;
        }
        
        this.skill.onUpdate(this, world, entity, skillSlot);
    }
    
    public EnumSpellAction onSkillPreparing(World world, EntityPlayer player, int duration)
    {
        IPlayer playerCap = player.getCapability(CapabilityLoader.player, null);
        if(playerCap.getCooldownTracker().getCooldown(this.getSkill()) > 0)
        {
            if(!world.isRemote)
                PlayerUtils.cooldown((EntityPlayerMP) player);
            return EnumSpellAction.NONE;
        }
        if(playerCap.getMana() < this.getSkillConsume())
        {
            if(!world.isRemote)
                PlayerUtils.nomana((EntityPlayerMP) player);
            return EnumSpellAction.NONE;
        }
        return this.getSkill().onSkillPreparing(this, world, player, duration);
    }
    
    public boolean onSkillSpell(World world, EntityPlayer player)
    {
        //if (!world.isRemote) return DawnEventFactory.onSpellSkillIntoWorld(this, player, world);
        boolean flag = this.getSkill().onSkillSpell(this, world, player);

        if (flag)
        {
            player.triggerAchievement(StatLoader.objectSpellStats[Skill.getIdFromSkill(this.skill)]);
        }

        return flag;
    }
    
    public EnumSpellAction onSkillSpelling(World world, EntityPlayer player, int duration)
    {
        IPlayer playerCap = player.getCapability(CapabilityLoader.player, null);
        if(playerCap.isCanceled())// TODO 找到打断施法的方法,然后移到那里
        {
            this.onPlayerStoppedSpelling(player.worldObj, player, playerCap.getSkillInSpellDuration());
            if(!world.isRemote)
                PlayerUtils.cancel((EntityPlayerMP) player);
            return EnumSpellAction.NONE;
        }
        return this.getSkill().onSkillSpelling(this, world, player, duration);
    }
    
    public void onPlayerStoppedSpelling(World world, EntityPlayer player, int duration)
    {
        this.getSkill().onPlayerStoppedSpelling(this, world, player, duration);
    }

    // 突然发现用这个能做个变身之类的能开关的技能啊666666
    public SkillStack onSkillSpellFinish(World world, EntityPlayer player)
    {
        return this.getSkill().onSkillSpellFinish(this, world, player);
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        ResourceLocation resourcelocation = Skill.skillRegistry.getNameForObject(this.skill);
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
        if (this.skillLevel < 1) this.skillLevel = 1;
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

    private boolean isSkillStackEqual(SkillStack other)
    {
        return this.skill == other.skill ? this.skillLevel == other.skillLevel ? true : false : false;
    }
    
    public static boolean areSkillStacksEqual(SkillStack stackA, SkillStack stackB)
    {
        return stackA == null && stackB == null ? true : stackA != null && stackB != null ? stackA.isSkillStackEqual(stackB) : false;
    }

    public boolean isSkillEqual(SkillStack other)
    {
        return other != null && this.skill == other.skill;
    }
    
    public static boolean areSkillsEqual(SkillStack stackA, SkillStack stackB)
    {
        return stackA == null && stackB == null ? true : stackA != null && stackB != null ? stackA.isSkillEqual(stackB) : false;
    }

    @Override
    public String toString()
    {
        return this.getUnlocalizedName() + "@" + this.skillLevel;
    }
}
