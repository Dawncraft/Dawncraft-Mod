package io.github.dawncraft.skill;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.potion.PotionLoader;
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
    public IChatComponent getChatComponent()
    {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.getDisplayName());
        IChatComponent ichatcomponent = new ChatComponentText("[").appendSibling(chatcomponenttext).appendText("]");

        if (this.getSkill() != null)
        {
            List<String> list = this.getTooltip(null, Minecraft.getMinecraft().gameSettings.advancedItemTooltips);
            String information = StringUtils.join(list.iterator(), "\n");
            ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(information)));
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
    
    public void updateAnimation(World worldIn, Entity entityIn, int skillSlot)
    {
        if (this.animationsToGo > 0)
        {
            --this.animationsToGo;
        }
        
        this.skill.onUpdate(this, worldIn, entityIn, skillSlot);
    }
    
    public EnumSpellResult onSkillPreparing(World worldIn, EntityPlayer playerIn, int duration)
    {
        IMagic magic = playerIn.getCapability(CapabilityLoader.magic, null);
        if(duration < 0 && magic.getPublicCooldownCount() > 0)
        {
            return EnumSpellResult.GLOBAL_COOLING;
        }
        if(duration >= 0 && magic.isCanceled())
        {
            return EnumSpellResult.CANCEL;
        }
        if(magic.getCooldownTracker().getCooldown(this.getSkill()) > 0)
        {
            return EnumSpellResult.COOLING;
        }
        if(playerIn.isPotionActive(PotionLoader.potionSilent))
        {
            return EnumSpellResult.SILENT;
        }
        if(magic.getMana() < this.getSkillConsume())
        {
            return EnumSpellResult.NOMANA;
        }
        return this.getSkill().onSkillPreparing(this, worldIn, playerIn, duration);
    }
    
    public boolean onSkillSpell(World worldIn, EntityPlayer playerIn)
    {
        //if (!worldIn.isRemote) return DawnEventFactory.onSpellSkillIntoWorld(this, playerIn, worldIn);
        boolean flag = this.getSkill().onSkillSpell(this, worldIn, playerIn);

        if (flag)
        {
            playerIn.triggerAchievement(StatLoader.objectSpellStats[Skill.getIdFromSkill(this.skill)]);
        }

        return flag;
    }
    
    public EnumSpellResult onSkillSpelling(World worldIn, EntityPlayer playerIn, int duration)
    {
        IMagic magic = playerIn.getCapability(CapabilityLoader.magic, null);
        if(magic.isCanceled())
        {
            this.onPlayerStoppedSpelling(worldIn, playerIn, duration);
            return EnumSpellResult.CANCEL;
        }
        if(playerIn.isPotionActive(PotionLoader.potionSilent))
        {
            return EnumSpellResult.SILENT;
        }
        return this.getSkill().onSkillSpelling(this, worldIn, playerIn, duration);
    }
    
    public void onPlayerStoppedSpelling(World worldIn, EntityPlayer playerIn, int duration)
    {
        this.getSkill().onPlayerStoppedSpelling(this, worldIn, playerIn, duration);
    }

    // 突然发现用这个能做个变身之类的能开关的技能啊666666
    public SkillStack onSkillSpellFinish(World worldIn, EntityPlayer playerIn)
    {
        return this.getSkill().onSkillSpellFinish(this, worldIn, playerIn);
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
