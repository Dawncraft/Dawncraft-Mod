package io.github.dawncraft.skill;

import java.util.List;

import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.api.skill.SkillRegistry;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.RegistryDelegate;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 技能基类
 *
 * @author QingChenW
 */
public class Skill
{
    private int maxLevel = 1;
    private String unlocalizedName;
    private CreativeSkillTabs tabToDisplayOn;
    
    public Skill() {}
    
    public Skill(int maxLevel)
    {
        this.maxLevel = maxLevel;
    }
    
    public float getConsume(SkillStack skillstack)
    {
        return this.getConsume(skillstack.getSkillLevel());
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

    public int getPrepare(SkillStack skillstack)
    {
        return this.getTotalPrepare(skillstack.getSkillLevel());
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
    public static int getPublicPrepare()
    {
        return ConfigLoader.publicPrepare;
    }
    
    public int getMaxDuration(SkillStack skillstack)
    {
        return this.getMaxDuration(skillstack.getSkillLevel());
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
    
    public int getCooldown(SkillStack skillstack)
    {
        return this.getCooldown(skillstack.getSkillLevel());
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

    /**
     * 全局技能冷却时间
     *
     * @return 时间刻
     */
    public static int getPublicCooldown()
    {
        return ConfigLoader.publicCooldown;
    }
    
    public int getLevel(SkillStack skillstack)
    {
        return skillstack.skillLevel;
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
    
    public Skill setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
        return this;
    }
    
    public String getSkillStackDisplayName(SkillStack skillstack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(skillstack) + ".name").trim();
    }
    
    public String getUnlocalizedNameInefficiently(SkillStack skillstack)
    {
        String s = this.getUnlocalizedName(skillstack);
        return s == null ? "" : StatCollector.translateToLocal(s);
    }
    
    /**
     * 这可能是个特例,你可能需要覆写它来为技能添加说明
     * <br>注意此处只有技能效果说明,有关技能的背景或故事或是其他文字请覆写{@link #addInformation(SkillStack, EntityPlayer, List, boolean)}}</br>
     *
     * @param skillstack 技能
     * @return 格式化过的字符串
     */
    public String getSkillStackDisplayDesc(SkillStack skillstack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName(skillstack) + ".desc");
    }
    
    public String getUnlocalizedName(SkillStack skillstack)
    {
        return this.getUnlocalizedName();
    }
    
    public String getUnlocalizedName()
    {
        return "skill." + this.unlocalizedName;
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
    public void addInformation(SkillStack skillstack, EntityPlayer player, List<String> tooltip, boolean advanced) {}
    
    /**
     * 和地图的类似,冷却移到了{@link SpellCooldownTracker}里,我也不知道这能干啥了
     *
     * @param stack 技能
     * @param worldIn 携带者所在世界
     * @param entityIn 技能携带者
     * @param skillSlot 技能槽位
     */
    public void onUpdate(SkillStack stack, World worldIn, Entity entityIn, int skillSlot)
    {
    }

    /**
     * 技能准备阶段每刻调用一次
     *
     * @param skillStack 正在准备的技能
     * @param worldIn 施放者所在世界
     * @param playerIn 施放玩家
     * @param duration 已经准备了多少刻
     * @return 准备施法的结果(如果返回值是COOLING以及下面的将会以该种原因取消技能准备)
     */
    public EnumSpellResult onSkillPreparing(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int duration)
    {
        return EnumSpellResult.NONE;
    }
    
    /**
     * 技能被施放时,所有技能都会在准备阶段结束后调用此方法
     *
     * @param skillstack 正在施放的技能
     * @param worldIn 施放者所在世界
     * @param playerIn 施放玩家
     * @return 是否施放成功(暂时无实际意义)
     */
    public boolean onSkillSpell(SkillStack skillstack, World worldIn, EntityPlayer playerIn)
    {
        return false;
    }
    
    /**
     * 如果Skill.getMaxDuration(int level) > 0,则认为该技能可持续施法,可持续施法的技能会在施法阶段每刻调用一次该方法
     *
     * @param skillStack 正在施放的技能
     * @param worldIn 施放者所在世界
     * @param playerIn 施放玩家
     * @param duration 已经施放了多少刻
     * @return 施放魔法的结果(如果返回值是COOLING以及下面的将会以该种原因取消技能施放)
     */
    public EnumSpellResult onSkillSpelling(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int duration)
    {
        return EnumSpellResult.NONE;
    }
    
    /**
     * 当可持续施法的技能被主动取消或由于外界不可抗拒(大雾)的因素而被强制性打断时调用此方法
     * <br>如果释放过程中未被打断且施放完成会调用{@link #onSkillSpellFinish(SkillStack skillStack, World world, EntityPlayer player)}</br>
     *
     * @param skillStack  正在施放的技能
     * @param worldIn 施放者所在世界
     * @param playerIn 施放玩家
     * @param duration 已经施放了多少刻
     */
    public void onPlayerStoppedSpelling(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int duration)
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

    /* ======================================== REGISTER START =====================================*/
    public static final RegistryNamespaced<ResourceLocation, Skill> skillRegistry = SkillRegistry.getSkillRegistry();
    public final RegistryDelegate<Skill> delegate = ((FMLControlledNamespacedRegistry)skillRegistry).getDelegate(this, Skill.class);

    public static int getIdFromSkill(Skill skill)
    {
        return skill == null ? 0 : skillRegistry.getIDForObject(skill);
    }
    
    public static Skill getSkillById(int id)
    {
        return skillRegistry.getObjectById(id);
    }
    
    public static Skill getByNameOrId(String id)
    {
        Skill skill = skillRegistry.getObject(new ResourceLocation(id));
        
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

    private ResourceLocation registryName = null;

    public final String getRegistryName()
    {
        if (this.delegate.getResourceName() != null) return this.delegate.getResourceName().toString();
        return this.registryName != null ? this.registryName.toString() : null;
    }

    public final Skill setRegistryName(String name)
    {
        if (this.getRegistryName() != null)
            throw new IllegalStateException("Attempted to set registry name on skill with exisiting registry name! New: " + name + " Old: " + this.getRegistryName());
        int index = name.lastIndexOf(':');
        String oldPrefix = index == -1 ? "" : name.substring(0, index);
        name = index == -1 ? name : name.substring(index + 1);
        net.minecraftforge.fml.common.ModContainer mc = net.minecraftforge.fml.common.Loader.instance().activeModContainer();
        String prefix = mc == null ? "minecraft" : mc.getModId();
        if (!oldPrefix.equals(prefix) && oldPrefix.length() > 0)
        {
            net.minecraftforge.fml.common.FMLLog.bigWarning("Dangerous alternative prefix %s for name %s, invalid registry invocation/invalid name?", name.substring(0, index), name);
            prefix = oldPrefix;
        }
        this.registryName = new ResourceLocation(prefix, name);
        return this;
    }
    public final Skill setRegistryName(ResourceLocation name){ return this.setRegistryName(name.toString()); }
    public final Skill setRegistryName(String modID, String name){ return this.setRegistryName(modID + ":" + name); }
    
    public final Class<SkillRegistry> getRegistryType()
    {
        return SkillRegistry.class;
    }
    /* ======================================== REGISTER END   =====================================*/
}
