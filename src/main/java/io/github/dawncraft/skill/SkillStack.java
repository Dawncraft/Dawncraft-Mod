package io.github.dawncraft.skill;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import io.github.dawncraft.command.CommandInit;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.stats.StatLoader;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * It is based on flyweight pattern, like {@link ItemStack}.
 * Every {@link SkillStack} contains a type of skill.
 *
 * @author QingChenW
 */
public class SkillStack
{
    public static final SkillStack EMPTY = new SkillStack(null);
    private Skill skill;
    public int skillLevel;
    /** A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items */
    private NBTTagCompound stackTagCompound;
    public int animationsToGo;

    public SkillStack(Skill skill)
    {
        this(skill, 1);
    }

    public SkillStack(Skill skill, int level)
    {
        this.skill = skill;
        this.skillLevel = MathHelper.clamp(level, 1, skill.getMaxLevel());
    }

    private SkillStack() {}

    public static SkillStack loadSkillStackFromNBT(NBTTagCompound nbt)
    {
        SkillStack stack = new SkillStack();
        stack.readFromNBT(nbt);
        return stack.getSkill() != null ? stack : null;
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
        return this.getSkill().getPrepare(this) + Skill.getGlobalPrepare();
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

    public boolean hasCustomName()
    {
        return this.stackTagCompound == null ? false : !this.stackTagCompound.hasKey("display", 10) ? false : this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8);
    }

    public String getDisplayName()
    {
        String name = this.getSkill().getSkillStackDisplayName(this);

        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10))
        {
            NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");

            if (nbttagcompound.hasKey("Name", 8))
            {
                name = nbttagcompound.getString("Name");
            }
        }

        return name;
    }

    public SkillStack setCustomName(String displayName)
    {
        if (this.stackTagCompound == null)
        {
            this.stackTagCompound = new NBTTagCompound();
        }

        if (!this.stackTagCompound.hasKey("display", 10))
        {
            this.stackTagCompound.setTag("display", new NBTTagCompound());
        }

        this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
        return this;
    }

    public void clearCustomName()
    {
        if (this.stackTagCompound != null)
        {
            if (this.stackTagCompound.hasKey("display", 10))
            {
                NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
                nbttagcompound.removeTag("Name");

                if (nbttagcompound.isEmpty())
                {
                    this.stackTagCompound.removeTag("display");

                    if (this.stackTagCompound.isEmpty())
                    {
                        this.setTagCompound(null);
                    }
                }
            }
        }
    }

    public String getDisplayDesc()
    {
        return this.getSkill().getSkillStackDisplayDesc(this);
    }

    public boolean hasTagCompound()
    {
        return this.stackTagCompound != null;
    }

    public void setTagCompound(NBTTagCompound nbt)
    {
        this.stackTagCompound = nbt;
    }

    public NBTTagCompound getTagCompound()
    {
        return this.stackTagCompound;
    }

    public NBTTagCompound getSubCompound(String key, boolean create)
    {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
        {
            return this.stackTagCompound.getCompoundTag(key);
        }
        else if (create)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            this.setTagInfo(key, nbttagcompound);
            return nbttagcompound;
        }
        else
        {
            return null;
        }
    }

    public void setTagInfo(String key, NBTBase value)
    {
        if (this.stackTagCompound == null)
        {
            this.setTagCompound(new NBTTagCompound());
        }

        this.stackTagCompound.setTag(key, value);
    }

    public ITextComponent getTextComponent()
    {
        TextComponentString name = new TextComponentString(this.getDisplayName());
        if (this.hasCustomName())
        {
            name.getStyle().setItalic(Boolean.valueOf(true));
        }

        ITextComponent text = new TextComponentString("[").appendSibling(name).appendText("]");

        if (this.skill != null)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            text.getStyle().setHoverEvent(new HoverEvent(CommandInit.SHOW_SKILL, new TextComponentString(tagCompound.toString())));
            text.getStyle().setColor(TextFormatting.AQUA);
        }

        return text;
    }

    @SideOnly(Side.CLIENT)
    public List<String> getTooltip(EntityPlayer player, boolean advanced)
    {
        List<String> list = Lists.<String>newArrayList();

        // Skill name and suffix
        String name = this.getDisplayName();
        if (this.hasCustomName())
        {
            name += TextFormatting.ITALIC;
        }

        name += TextFormatting.RESET;

        if (advanced)
        {
            name += String.format(" (#%04d)", Skill.getIdFromSkill(this.skill));
        }

        list.add(name);

        // Tooltip hide flags
        // TODO Hide flags 未实现
        int flag = 0;

        if (this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99))
        {
            flag = this.stackTagCompound.getInteger("HideFlags");
        }

        // Skill level
        list.add(I18n.format("skill.level", this.getSkillLevel(), this.getMaxLevel()));

        // Skill description
        list.add(this.getDisplayDesc());

        this.skill.addInformation(this, player, list, advanced);

        if (this.hasTagCompound() && this.stackTagCompound.hasKey("display", 10))
        {
            NBTTagCompound nbt = this.stackTagCompound.getCompoundTag("display");

            if (nbt.getTagId("Lore") == 9)
            {
                NBTTagList nbtList = nbt.getTagList("Lore", 8);

                if (nbtList.tagCount() > 0)
                {
                    for (int i = 0; i < nbtList.tagCount(); ++i)
                    {
                        list.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + nbtList.getStringTagAt(i));
                    }
                }
            }
        }

        // The next level of skill
        if (this.getSkillLevel() < this.getMaxLevel())
        {
            if (Keyboard.isKeyDown(KeyLoader.USE.getKeyCode()))
            {
                list.add(I18n.format("skill.nextLevel"));
                SkillStack skillStack = this.copy();
                ++skillStack.skillLevel;
                list.add(skillStack.getDisplayDesc());
            }
            else
            {
                list.add(TextFormatting.GRAY + I18n.format("skill.moreInfo", Keyboard.getKeyName(KeyLoader.USE.getKeyCode())));
            }
        }
        else
        {
            list.add(TextFormatting.GREEN + I18n.format("skill.maxLevel", Keyboard.getKeyName(KeyLoader.USE.getKeyCode())));
        }

        // NBT tag count
        if (advanced)
        {
            list.add(TextFormatting.DARK_GRAY + Skill.REGISTRY.getNameForObject(this.getSkill()).toString());
            if (this.hasTagCompound())
            {
                list.add(TextFormatting.DARK_GRAY + I18n.format("skill.nbtTags", 0));
            }
        }

        return list;
    }

    public void onLearning(World world, EntityPlayer player)
    {
        player.addStat(StatLoader.objectLearnStats[Skill.getIdFromSkill(this.skill)], 1);

        // 成就可以在这里写

        this.skill.onCreated(this, world, player);
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
        return this.getSkill().onSkillPreparing(this, world, player, duration);
    }

    public boolean onSkillSpell(World world, EntityPlayer player)
    {
        boolean flag = this.getSkill().onSkillSpell(this, world, player);

        if (flag)
        {
            player.addStat(StatLoader.objectLearnStats[Skill.getIdFromSkill(this.skill)]);
        }

        return flag;
    }

    public EnumSpellAction onSkillSpelling(World world, EntityPlayer player, int duration)
    {
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
        ResourceLocation resourcelocation = Skill.REGISTRY.getNameForObject(this.skill);
        nbt.setString("id", resourcelocation == null ? "minecraft:null" : resourcelocation.toString());
        nbt.setByte("Level", (byte) this.skillLevel);
        if (this.stackTagCompound != null)
        {
            nbt.setTag("tag", this.stackTagCompound);
        }
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
        this.skillLevel = MathHelper.clamp(nbt.getByte("Level"), 1, this.getMaxLevel());
        if (nbt.hasKey("tag", 10))
        {
            this.stackTagCompound = nbt.getCompoundTag("tag");
        }
    }

    public SkillStack copy()
    {
        SkillStack stack = new SkillStack(this.skill, this.skillLevel);
        if (this.stackTagCompound != null)
        {
            stack.stackTagCompound = this.stackTagCompound.copy();
        }
        return stack;
    }

    public static SkillStack copySkillStack(SkillStack stack)
    {
        return stack == null ? null : stack.copy();
    }

    public boolean isSkillEqual(SkillStack other)
    {
        return other != null && this.skill == other.skill && this.skillLevel == other.skillLevel;
    }

    public static boolean areSkillsEqual(SkillStack stackA, SkillStack stackB)
    {
        return stackA != null && stackB != null ? stackA.isSkillEqual(stackB) : false;
    }

    public boolean isSkillStackEqual(SkillStack other)
    {
        return other != null && this.isSkillEqual(other) && (this.stackTagCompound != null && other.stackTagCompound != null ? this.stackTagCompound.equals(other.stackTagCompound) : false);
    }

    public static boolean areSkillStacksEqual(SkillStack stackA, SkillStack stackB)
    {
        return stackA == null && stackB == null ? true : stackA != null && stackB != null ? stackA.isSkillStackEqual(stackB) : false;
    }

    @Override
    public String toString()
    {
        return this.getUnlocalizedName() + "@" + this.skillLevel;
    }

    public boolean isEmpty()
    {
        if (this == EMPTY)
        {
            return true;
        }
        else if (this.getSkill() != null)
        {
            if (this.skillLevel <= 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }
}
