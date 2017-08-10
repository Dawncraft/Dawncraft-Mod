package io.github.dawncraft.skill;

import java.util.List;
import java.util.Random;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMana;
import io.github.dawncraft.creativetab.CreativeSkillTabs;
import io.github.dawncraft.network.MessageMana;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.api.SkillRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author QingChenW
 *
 */
public class Skill
{
    private int consume;
    private String unlocalizedName;
    private CreativeSkillTabs tabToDisplayOn;

    public Skill setConsumeMana(int mana)
    {
        this.consume = mana;
        return this;
    }
    
    public int getConsumeMana()
    {
        return this.consume;
    }
    
    public Skill setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public String getUnlocalizedNameInefficiently(ItemStack stack)
    {
        String s = this.getUnlocalizedName(stack);
        return s == null ? "" : StatCollector.translateToLocal(s);
    }

    public String getUnlocalizedName()
    {
        return "item." + this.unlocalizedName;
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return this.getUnlocalizedName();
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(/*替换成skill stack*/Skill skill, EntityPlayer player, List<String> tooltip, boolean advanced) {}
    
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

    public boolean onSkillSpell(/*替换成skill stack*/Skill skill, EntityPlayer player, World world)
    {
        if(!world.isRemote && player.hasCapability(CapabilityLoader.mana, null))
        {
            IMana manaCap = player.getCapability(CapabilityLoader.mana, null);
            int mp = manaCap.getMana();
            if(mp >= 4)
            {
                mp = mp - 4;
                player.heal(12.0F);
                manaCap.setMana(mp);

                MessageMana message = new MessageMana();
                message.nbt.setInteger("mana", mp);
                NetworkLoader.instance.sendTo(message, (EntityPlayerMP) player);

                for(int i = 0; i < 4; i++)
                {
                    Random rand = new Random();
                    double d0 = player.getPosition().getX() + rand.nextFloat();
                    double d1 = player.getPosition().getY() + 0.8F;
                    double d2 = player.getPosition().getZ() + rand.nextFloat();
                    double d3 = 0.0D;
                    double d4 = 0.0D;
                    double d5 = 0.0D;
                    world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, d0, d1, d2, d3, d4, d5, new int[0]);
                }

                this.onSkillSpellFinish(/*替换成skill stack*/skill, world, player);
                return true;
            }
        }
        return false;
    }

    public void onSkillSpellFinish(/*替换成skill stack*/Skill skill, World world, EntityPlayer player)
    {
        
    }
    
    /* ======================================== REGISTER START =====================================*/
    public static final RegistryNamespaced<ResourceLocation, Skill> skillRegistry = SkillRegistry.getSkillRegistry();
    public final net.minecraftforge.fml.common.registry.RegistryDelegate<Skill> delegate =
            ((net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry)skillRegistry).getDelegate(this, Skill.class);
    
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
            catch (NumberFormatException e)
            {
                ;
            }
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
