package io.github.dawncraft.skill;

import java.util.Random;

import io.github.dawncraft.api.skill.SkillRegistry;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.network.MessageUpdateMana;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author QingChenW
 *
 */
public class SkillLoader
{
    public static Skill attack = new Skill(2)
    {
        @Override
        public float getConsume(int level)
        {
            return 2 + 1 * level;
        }
        
        @Override
        public int getTotalCooldown(int level)
        {
            return 60 + 20 * level;
        }
        
        @Override
        public boolean onSkillSpell(SkillStack skillstack, EntityPlayer player, World world)
        {
            if(!world.isRemote && player.hasCapability(CapabilityLoader.magic, null))
            {
                IMagic manaCap = player.getCapability(CapabilityLoader.magic, null);
                float mp = manaCap.getMana();
                if(mp >= skillstack.getSkillConsume())
                {
                    mp = mp - skillstack.getSkillConsume();
                    player.attackEntityFrom(DamageSourceLoader.causeSkillDamage(skillstack, player), 2.0F + 1.0F * this.getLevel(skillstack));
                    manaCap.setMana(mp);
                    
                    NetworkLoader.instance.sendTo(new MessageUpdateMana(mp), (EntityPlayerMP) player);
                    
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
                    
                    return true;
                }
            }
            return false;
        }
    }.setUnlocalizedName("attack").setCreativeTab(CreativeTabsLoader.tabSkills);
    public static Skill heal = new Skill(2)
    {
        @Override
        public float getConsume(int level)
        {
            return 4 + 2 * level;
        }

        @Override
        public int getTotalCooldown(int level)
        {
            return 40 + 20 * level;
        }

        @Override
        public boolean onSkillSpell(SkillStack skillstack, EntityPlayer player, World world)
        {
            if(!world.isRemote && player.hasCapability(CapabilityLoader.magic, null))
            {
                IMagic manaCap = player.getCapability(CapabilityLoader.magic, null);
                float mp = manaCap.getMana();
                if(mp >= skillstack.getSkillConsume())
                {
                    mp = mp - skillstack.getSkillConsume();
                    player.heal(4.0F + 2.0F * this.getLevel(skillstack));
                    manaCap.setMana(mp);
                    
                    NetworkLoader.instance.sendTo(new MessageUpdateMana(mp), (EntityPlayerMP) player);
                    
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
                    
                    return true;
                }
            }
            return false;
        }
    }.setUnlocalizedName("heal").setCreativeTab(CreativeTabsLoader.tabSkills);

    public SkillLoader(FMLPreInitializationEvent event)
    {
        register(attack, "attack");
        register(heal, "heal");
    }

    /**
     * Register a skill with a name-id.
     *
     * @param skill
     *            The skill to register
     * @param name
     *            The skill's name-id
     */
    private static void register(Skill skill, String name)
    {
        SkillRegistry.registerSkill(skill, name);
    }
}
