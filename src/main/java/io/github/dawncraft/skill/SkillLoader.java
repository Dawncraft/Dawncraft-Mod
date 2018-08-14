package io.github.dawncraft.skill;

import java.util.List;
import java.util.Random;

import io.github.dawncraft.api.ModRegistry;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author QingChenW
 *
 */
public class SkillLoader
{
    public static Skill attack = new Skill(3)
    {
        @Override
        public float getConsume(int level)
        {
            return 2 + 1 * level;
        }

        @Override
        public int getCooldown(int level)
        {
            return 60 + 20 * level;
        }

        @Override
        public boolean onSkillSpell(SkillStack skillstack, World world, EntityPlayer player)
        {
            if(!world.isRemote)
            {
                player.attackEntityFrom(DamageSourceLoader.causeSkillDamage(skillstack, player), 2.0F + 1.0F * this.getLevel(skillstack));

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
            return false;
        }
    }.setUnlocalizedName("attack").setCreativeTab(CreativeTabsLoader.tabSkills);
    public static Skill heal = new Skill(3)
    {
        @Override
        public float getConsume(int level)
        {
            return 4 + 2 * level;
        }

        @Override
        public int getCooldown(int level)
        {
            return 40 + 20 * level;
        }
        
        @Override
        public String getSkillStackDisplayDesc(SkillStack skillstack)
        {
            return StatCollector.translateToLocalFormatted(this.getUnlocalizedName(skillstack) + ".desc",
                    skillstack.getSkillConsume(), 4.0F + 2.0F * this.getLevel(skillstack), skillstack.getTotalCooldown());
        }

        @Override
        public void addInformation(SkillStack skillstack, EntityPlayer player, List<String> tooltip, boolean advanced)
        {
            tooltip.add(StatCollector.translateToLocal(this.getUnlocalizedName(skillstack) + ".desc2"));
        }
        
        @Override
        public boolean onSkillSpell(SkillStack skillstack, World world, EntityPlayer player)
        {
            if(!world.isRemote)
            {
                player.heal(4.0F + 2.0F * this.getLevel(skillstack));

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
            return false;
        }
    }.setUnlocalizedName("heal").setCreativeTab(CreativeTabsLoader.tabSkills);

    public static Skill longPrepare = new Skill()
    {
        @Override
        public int getTotalPrepare(int level)
        {
            return 80;
        }
        
        @Override
        public EnumSpellAction onSkillPreparing(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int duration)
        {
            return EnumSpellAction.PREPAR;
        }

        @Override
        public boolean onSkillSpell(SkillStack skillstack, World worldIn, EntityPlayer playerIn)
        {
            return true;
        }

    }.setUnlocalizedName("prepare").setCreativeTab(CreativeTabsLoader.tabSkills);
    
    public static Skill longSpell = new Skill()
    {
        @Override
        public int getMaxDuration(int level)
        {
            return 400;
        }

        @Override
        public boolean onSkillSpell(SkillStack skillstack, World worldIn, EntityPlayer playerIn)
        {
            return super.onSkillSpell(skillstack, worldIn, playerIn);
        }
        
        @Override
        public EnumSpellAction onSkillSpelling(SkillStack skillStack, World worldIn, EntityPlayer playerIn,
                int duration)
        {
            return super.onSkillSpelling(skillStack, worldIn, playerIn, duration);
        }

        @Override
        public void onPlayerStoppedSpelling(SkillStack skillStack, World worldIn, EntityPlayer playerIn, int duration)
        {
            super.onPlayerStoppedSpelling(skillStack, worldIn, playerIn, duration);
        }

        @Override
        public SkillStack onSkillSpellFinish(SkillStack skillStack, World world, EntityPlayer player)
        {
            return super.onSkillSpellFinish(skillStack, world, player);
        }

    }.setUnlocalizedName("spell").setCreativeTab(CreativeTabsLoader.tabSkills);
    
    public static Skill longCooldown = new Skill()
    {
        @Override
        public int getCooldown(int level)
        {
            return 80;
        }

        @Override
        public boolean onSkillSpell(SkillStack skillstack, World worldIn, EntityPlayer playerIn)
        {
            return true;
        }
    }.setUnlocalizedName("cooldown").setCreativeTab(CreativeTabsLoader.tabSkills);

    public SkillLoader(FMLPreInitializationEvent event)
    {
        register(attack, "attack");
        register(heal, "heal");
        register(longPrepare, "prepare");
        register(longSpell, "spell");
        register(longCooldown, "cooldown");
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
        ModRegistry.registerSkill(skill, name);
    }
}
