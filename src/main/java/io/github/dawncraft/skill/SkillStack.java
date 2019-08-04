package io.github.dawncraft.skill;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import io.github.dawncraft.CommonProxy;
import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.stats.StatInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
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
    public static final SkillStack EMPTY = new SkillStack((Skill) null);

    private Skill skill;
    /** Level of the stack. */
    private int stackLevel;
    /** A NBTTagCompound containing data about a SkillStack.*/
    private NBTTagCompound stackTagCompound;
    public int animationsToGo;

    public SkillStack(Skill skill)
    {
        this(skill, 1);
    }

    public SkillStack(Skill skill, int level)
    {
        this.skill = skill;
        this.stackLevel = this.skill != null ? MathHelper.clamp(level, 1, skill.getMaxLevel()) : 1;
    }

    public SkillStack(NBTTagCompound compound)
    {
        this.readFromNBT(compound);
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

    public int getMaxLevel()
    {
        return this.getSkill().getMaxLevel();
    }

    public int getLevel()
    {
        return Math.min(1, this.stackLevel);
    }

    public void setLevel(int level)
    {
        this.stackLevel = level;
    }

    public void levelup(int amount)
    {
        this.setLevel(this.stackLevel + amount);
    }

    public void leveldown(int amount)
    {
        this.levelup(-amount);
    }

    public String getTranslationKey()
    {
        return this.getSkill().getTranslationKey(this);
    }

    @Override
    public String toString()
    {
        return this.getTranslationKey() + "@" + this.stackLevel;
    }

    public boolean hasDisplayName()
    {
        NBTTagCompound compound = this.getSubCompound("display", false);
        return compound != null && compound.hasKey("Name", 8);
    }

    public String getDisplayName()
    {
        NBTTagCompound compound = this.getSubCompound("display", false);
        if (compound != null)
        {
            if (compound.hasKey("Name", 8))
            {
                return compound.getString("Name");
            }

            if (compound.hasKey("LocName", 8))
            {
                return I18n.format(compound.getString("LocName"));
            }
        }
        return this.getSkill().getSkillStackDisplayName(this);
    }

    public SkillStack setTranslatableName(String translatableKey)
    {
        this.getSubCompound("display", true).setString("LocName", translatableKey);
        return this;
    }

    public SkillStack setCustomName(String displayName)
    {
        this.getSubCompound("display", true).setString("Name", displayName);
        return this;
    }

    public void clearCustomName()
    {
        NBTTagCompound compound = this.getSubCompound("display", false);
        if (compound != null)
        {
            compound.removeTag("Name");
            if (compound.isEmpty())
            {
                this.removeSubCompound("display");
            }
        }
        if (this.stackTagCompound != null && this.stackTagCompound.isEmpty())
        {
            this.stackTagCompound = null;
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

    public void setTagCompound(@Nullable NBTTagCompound nbt)
    {
        this.stackTagCompound = nbt;
    }

    public NBTTagCompound getTagCompound()
    {
        return this.stackTagCompound;
    }

    @Nullable
    public NBTTagCompound getSubCompound(String key, boolean create)
    {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
        {
            return this.stackTagCompound.getCompoundTag(key);
        }
        else if (create)
        {
            NBTTagCompound compound = new NBTTagCompound();
            this.setTagInfo(key, compound);
            return compound;
        }
        else
        {
            return null;
        }
    }

    public void removeSubCompound(String key)
    {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
        {
            this.stackTagCompound.removeTag(key);
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

    public int getAnimationsToGo()
    {
        return this.animationsToGo;
    }

    public void setAnimationsToGo(int animations)
    {
        this.animationsToGo = animations;
    }

    public ITextComponent getTextComponent()
    {
        TextComponentString name = new TextComponentString(this.getDisplayName());
        if (this.hasDisplayName())
        {
            name.getStyle().setItalic(Boolean.valueOf(true));
        }

        ITextComponent text = new TextComponentString("[").appendSibling(name).appendText("]");

        if (this.skill != null)
        {
            NBTTagCompound compound = this.writeToNBT(new NBTTagCompound());
            text.getStyle().setHoverEvent(new HoverEvent(CommonProxy.SHOW_SKILL, new TextComponentString(compound.toString())));
            text.getStyle().setColor(this.getSkill().getRarity(this).getColor());
        }

        return text;
    }

    @SideOnly(Side.CLIENT)
    public List<String> getTooltip(EntityPlayer player, ITooltipFlag advanced)
    {
        List<String> list = Lists.<String>newArrayList();
        // Skill name and suffix
        String name = this.getDisplayName();
        if (this.hasDisplayName())
        {
            name += TextFormatting.ITALIC;
        }

        name += TextFormatting.RESET;

        if (advanced.isAdvanced())
        {
            name += String.format(" (#%04d)", Skill.getIdFromSkill(this.skill));
        }

        list.add(name);
        // Tooltip hide flags
        // TODO 技能 Hide flags 未实现
        int flag = 0;

        if (this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99))
        {
            flag = this.stackTagCompound.getInteger("HideFlags");
        }

        // Skill level
        list.add(I18n.format("skill.level", this.getLevel(), this.getMaxLevel()));

        // Skill description
        list.add(this.getDisplayDesc());

        this.getSkill().addInformation(this, player, list, advanced);

        if (this.hasTagCompound())
        {
            if (this.stackTagCompound.hasKey("display", 10))
            {
                NBTTagCompound nbt = this.stackTagCompound.getCompoundTag("display");
                if (nbt.getTagId("Lore") == 9)
                {
                    NBTTagList nbtList = nbt.getTagList("Lore", 8);
                    if (!nbtList.isEmpty())
                    {
                        for (int i = 0; i < nbtList.tagCount(); ++i)
                        {
                            list.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + nbtList.getStringTagAt(i));
                        }
                    }
                }
            }
        }

        // The next level of skill
        if (this.getLevel() < this.getMaxLevel())
        {
            if (Keyboard.isKeyDown(KeyLoader.USE.getKeyCode()))
            {
                list.add(I18n.format("skill.nextLevel"));
                SkillStack skillStack = this.copy();
                skillStack.levelup(1);
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
        if (advanced.isAdvanced())
        {
            list.add(TextFormatting.DARK_GRAY + this.getSkill().getRegistryName().toString());
            if (this.hasTagCompound())
            {
                list.add(TextFormatting.DARK_GRAY + I18n.format("skill.nbt_tags", this.getTagCompound().getKeySet().size()));
            }
        }

        DawnEventFactory.onSkillTooltip(this, player, list, advanced);
        return list;
    }

    public void onLearning(World world, EntityPlayer player)
    {
        player.addStat(StatInit.getLearnStats(this.skill));
        this.skill.onCreated(this, world, player);
    }

    public void updateAnimation(World world, Entity entity, int skillSlot)
    {
        if (this.animationsToGo > 0)
        {
            --this.animationsToGo;
        }

        if (this.skill != null)
        {
            this.skill.onUpdate(this, world, entity, skillSlot);
        }
    }

    public EnumActionResult onSkillPreparing(World world, EntityPlayer player, int duration)
    {
        return this.getSkill().onSkillPreparing(this, world, player, duration);
    }

    public EnumActionResult onSkillSpell(World world, EntityPlayer player)
    {
        EnumActionResult result = this.getSkill().onSkillSpell(this, world, player);
        if (result == EnumActionResult.SUCCESS)
        {
            player.addStat(StatInit.getObjectSpellStats(this.skill));
        }
        return result;
    }

    public EnumActionResult onSkillSpelling(World world, EntityPlayer player, int duration)
    {
        return this.getSkill().onSkillSpelling(this, world, player, duration);
    }

    public void onPlayerStoppedSpelling(World world, EntityPlayer player, int duration)
    {
        this.getSkill().onPlayerStoppedSpelling(this, world, player, duration);
    }

    public SkillStack onSkillSpellFinish(World world, EntityPlayer player)
    {
        return this.getSkill().onSkillSpellFinish(this, world, player);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        ResourceLocation id = this.skill.getRegistryName();
        nbt.setString("id", id == null ? Dawncraft.MODID + ":" + "null" : id.toString());
        nbt.setByte("Level", (byte) this.stackLevel);
        if (this.stackTagCompound != null)
        {
            nbt.setTag("tag", this.stackTagCompound);
        }
        return nbt;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        this.skill = nbt.hasKey("id", 8) ? Skill.getByNameOrId(nbt.getString("id")) : null;
        this.stackLevel = this.skill != null ? MathHelper.clamp(nbt.getByte("Level"), 1, this.getMaxLevel()) : 1;
        if (nbt.hasKey("tag", 10))
        {
            this.stackTagCompound = nbt.getCompoundTag("tag");

            if (this.skill != null)
            {
                this.skill.updateSkillStackNBT(nbt);
            }
        }
    }

    public SkillStack copy()
    {
        SkillStack stack = new SkillStack(this.skill, this.stackLevel);
        stack.setAnimationsToGo(this.getAnimationsToGo());
        if (this.stackTagCompound != null)
        {
            stack.stackTagCompound = this.stackTagCompound.copy();
        }
        return stack;
    }

    public boolean isSkillEqual(SkillStack other)
    {
        return other != null && this.skill == other.skill && this.stackLevel == other.stackLevel;
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

    public static boolean areSkillStackTagsEqual(SkillStack stackA, SkillStack stackB)
    {
        if (stackA == null && stackB == null)
        {
            return true;
        }
        else if (stackA != null && stackB != null)
        {
            if (stackA.stackTagCompound == null && stackB.stackTagCompound != null)
            {
                return false;
            }
            else
            {
                return stackA.stackTagCompound == null || stackA.stackTagCompound.equals(stackB.stackTagCompound);
            }
        }
        else
        {
            return false;
        }
    }

    public boolean isEmpty()
    {
        if (this == EMPTY)
        {
            return true;
        }
        else if (this.getSkill() != null)
        {
            if (this.stackLevel <= 1)
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
