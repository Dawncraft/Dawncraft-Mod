package io.github.dawncraft.skill;

import java.util.List;

import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.api.skill.SkillRegistry;
import io.github.dawncraft.config.ConfigLoader;
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
 * @author QingChenW
 *
 */
public class Skill
{
    private int maxLevel = 0;
    private String unlocalizedName;
    private CreativeSkillTabs tabToDisplayOn;
    
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

    public int getTotalPrepare(SkillStack skillstack)
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
        return skillstack.cooldown;
    }
    
    public int getTotalCooldown(SkillStack skillstack)
    {
        return this.getTotalCooldown(skillstack.getSkillLevel());
    }

    /**
     * 技能冷却时间
     *
     * @param level 等级
     * @return 时间刻
     */
    public int getTotalCooldown(int level)
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
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(skillstack) + ".name")).trim();
    }
    
    public String getUnlocalizedNameInefficiently(SkillStack skillstack)
    {
        String s = this.getUnlocalizedName(skillstack);
        return s == null ? "" : StatCollector.translateToLocal(s);
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
    
    public boolean onSkillSpell(SkillStack skillstack, EntityPlayer player, World world)
    {
        return false;
    }
    
    /**
     * 和地图的类似,但这个主要用来更新冷却
     *
     * @param stack
     * @param worldIn
     * @param entityIn
     * @param skillSlot
     */
    public void onUpdate(SkillStack stack, World worldIn, Entity entityIn, int skillSlot)
    {
        if(stack != null)
        {
            if(stack.getCooldown() > 0) stack.cooldown--;
        }
    }
    
    public SkillStack onSkillSpellFinish(SkillStack skillstack, World world, EntityPlayer player)
    {
        return skillstack;
    }
    
    public void onPlayerStoppedSpelling(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        
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
