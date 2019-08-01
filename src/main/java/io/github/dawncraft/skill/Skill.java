package io.github.dawncraft.skill;

import java.util.List;

import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.potion.PotionInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * The skill class
 *
 * @author QingChenW
 */
public class Skill extends IForgeRegistryEntry.Impl<Skill>
{
    private int maxLevel = 1;
    private String unlocalizedName;
    private CreativeSkillTabs tabToDisplayOn;

    public Skill() {}

    public Skill(int maxLevel)
    {
        this.maxLevel = maxLevel;
    }

    public float getConsume(SkillStack skillStack)
    {
        return this.getConsume(skillStack.getSkillLevel());
    }

    /**
     * 技能消耗
     *
     * @param level 技能等级
     * @return 消耗的魔法值
     */
    public float getConsume(int level)
    {
        return 0.0F;
    }

    public int getPrepare(SkillStack skillStack)
    {
        return this.getTotalPrepare(skillStack.getSkillLevel());
    }

    /**
     * 技能施法前摇时间
     *
     * @param level 等级
     * @return 时间刻
     */
    public int getTotalPrepare(int level)
    {
        return 0;
    }

    /**
     * 全局施法前摇时间
     *
     * @return 时间刻
     */
    public static int getGlobalPrepare()
    {
        return ConfigLoader.globalPrepareTicks;
    }

    public int getMaxDuration(SkillStack skillStack)
    {
        return this.getMaxDuration(skillStack.getSkillLevel());
    }

    /**
     * 技能持续时间
     *
     * @param level 等级
     * @return 时间刻
     */
    public int getMaxDuration(int level)
    {
        return 0;
    }

    /* 后摇?不存在的,为了简化机制,所以没有后摇啊哈哈哈 */
    // TODO 为技能添加后摇

    public int getCooldown(SkillStack skillStack)
    {
        return this.getCooldown(skillStack.getSkillLevel());
    }

    /**
     * 技能冷却时间
     *
     * @param level 等级
     * @return 时间刻
     */
    public int getCooldown(int level)
    {
        return 0;
    }

    public int getLevel(SkillStack skillStack)
    {
        return skillStack.skillLevel;
    }

    public void setLevel(SkillStack skillStack, int level)
    {
        skillStack.skillLevel = level;
    }

    public int getMaxLevel()
    {
        return this.maxLevel;
    }

    public Skill setMaxLevel(int maxLevel)
    {
        this.maxLevel = maxLevel;
        return this;
    }

    public String getUnlocalizedName(SkillStack skillStack)
    {
        return this.getUnlocalizedName();
    }

    public String getUnlocalizedName()
    {
        return "skill." + this.unlocalizedName;
    }

    public Skill setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public String getUnlocalizedNameInefficiently(SkillStack skillStack)
    {
        String s = this.getUnlocalizedName(skillStack);
        return s == null ? "" : I18n.format(s);
    }

    public String getSkillStackDisplayName(SkillStack skillStack)
    {
        return I18n.format(this.getUnlocalizedNameInefficiently(skillStack) + ".name").trim();
    }

    public String getUnlocalizedDesc(SkillStack skillStack)
    {
        return this.getUnlocalizedName(skillStack) + ".desc";
    }

    /**
     * 这可能是个特例,你可能需要覆写它来为技能添加说明
     * <br>注意此处只有技能效果说明,有关技能的背景或故事或是其他文字请覆写{@link #addInformation(SkillStack, EntityPlayer, List, boolean)}}</br>
     *
     * @param skillStack 技能
     * @return 格式化过的字符串
     */
    public String getSkillStackDisplayDesc(SkillStack skillStack)
    {
        return I18n.format(this.getUnlocalizedDesc(skillStack));
    }

    public Skill setCreativeTab(CreativeSkillTabs tab)
    {
        this.tabToDisplayOn = tab;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public CreativeSkillTabs getCreativeTab()
    {
        return this.tabToDisplayOn;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromSkillStack(SkillStack skillStack, int renderPass)
    {
        return 0xFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    public boolean canSpell(SkillStack skillStack, EntityPlayer player)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(SkillStack skillStack, EntityPlayer player, List<String> tooltip, boolean advanced) {}

    /**
     * 当技能被学习时出发
     *
     * @param skillStack 技能
     * @param world 携带者所在世界
     * @param player 技能携带者
     */
    public void onCreated(SkillStack skillStack, World world, EntityPlayer player)
    {
    }

    /**
     * 和地图的类似,冷却移到了{@link SpellCooldownTracker}里,我也不知道这能干啥了
     *
     * @param skillStack 技能
     * @param world 携带者所在世界
     * @param entity 技能携带者
     * @param skillSlot 技能槽位
     */
    public void onUpdate(SkillStack skillStack, World world, Entity entity, int skillSlot)
    {
    }

    /**
     * 技能准备阶段每刻调用一次
     *
     * @param skillStack 正在准备的技能
     * @param world 施放者所在世界
     * @param player 施放玩家
     * @param duration 已经准备了多少刻
     * @return 准备施法的结果(如果返回值是NONE则取消技能准备)
     */
    public EnumSpellAction onSkillPreparing(SkillStack skillStack, World world, EntityPlayer player, int duration)
    {
        boolean isInit = duration == 0;
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        if (playerMagic.getCooldownTracker().isGlobalCooldown())
        {
            playerMagic.sendCancelSpellReason(new TextComponentTranslation("gui.skill.globalcool"), !isInit);
            return EnumSpellAction.NONE;
        }
        if (playerMagic.getCooldownTracker().getCooldown(this) > 0)
        {
            playerMagic.sendCancelSpellReason(new TextComponentTranslation("gui.skill.cool"), !isInit);
            return EnumSpellAction.NONE;
        }
        if (!player.capabilities.isCreativeMode && playerMagic.getMana() < this.getConsume(skillStack))
        {
            playerMagic.sendCancelSpellReason(new TextComponentTranslation("gui.skill.nomana"), !isInit);
            return EnumSpellAction.NONE;
        }
        if (player.getActivePotionEffect(PotionInit.SILENT) != null)
        {
            playerMagic.sendCancelSpellReason(new TextComponentTranslation("gui.skill.silent"), !isInit);
            return EnumSpellAction.NONE;
        }
        return EnumSpellAction.PREPARE;
    }

    /**
     * 技能被施放时,所有技能都会在准备阶段结束后调用此方法
     *
     * @param skillStack 正在施放的技能
     * @param world 施放者所在世界
     * @param player 施放玩家
     * @return 是否施放成功(暂时无实际意义)
     */
    public boolean onSkillSpell(SkillStack skillStack, World world, EntityPlayer player)
    {
        return false;
    }

    /**
     * 如果Skill.getMaxDuration(int level) > 0,则认为该技能可持续施法,可持续施法的技能会在施法阶段每刻调用一次该方法
     *
     * @param skillStack 正在施放的技能
     * @param world 施放者所在世界
     * @param player 施放玩家
     * @param duration 已经施放了多少刻
     * @return 施放魔法的结果(如果返回值是NONE则取消技能施放)
     */
    public EnumSpellAction onSkillSpelling(SkillStack skillStack, World world, EntityPlayer player, int duration)
    {
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        if (player.getActivePotionEffect(PotionInit.SILENT) != null)
        {
            playerMagic.sendCancelSpellReason(new TextComponentTranslation("gui.skill.silent"), true);
            return EnumSpellAction.NONE;
        }
        return EnumSpellAction.SPELL;
    }

    /**
     * 当可持续施法的技能被主动取消或由于外界不可抗拒(大雾)的因素而被强制性打断时调用此方法
     * <br>如果释放过程中未被打断且施放完成会调用{@link #onSkillSpellFinish(SkillStack skillStack, World world, EntityPlayer player)}</br>
     *
     * @param skillStack  正在施放的技能
     * @param world 施放者所在世界
     * @param player 施放玩家
     * @param duration 已经施放了多少刻
     */
    public void onPlayerStoppedSpelling(SkillStack skillStack, World world, EntityPlayer player, int duration)
    {

    }

    /**
     * 当可持续施法的技能自然地完成施法(施法时间达到最大周期)时调用此方法
     * <br>如果释放过程未完成且被打断会调用{@link #onPlayerStoppedSpelling(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int duration)}</br>
     *
     * @param skillStack
     * @param world
     * @param player
     * @return
     */
    public SkillStack onSkillSpellFinish(SkillStack skillStack, World world, EntityPlayer player)
    {
        return skillStack;
    }

    public static final RegistryNamespaced<ResourceLocation, Skill> REGISTRY = GameData.getWrapper(Skill.class);

    public static int getIdFromSkill(Skill skill)
    {
        return skill == null ? 0 : REGISTRY.getIDForObject(skill);
    }

    public static Skill getSkillById(int id)
    {
        return REGISTRY.getObjectById(id);
    }

    public static Skill getByNameOrId(String id)
    {
        Skill skill = REGISTRY.getObject(new ResourceLocation(id));
        if (skill == null)
        {
            try
            {
                return getSkillById(Integer.parseInt(id));
            }
            catch (NumberFormatException e) {}
        }
        return skill;
    }
}
