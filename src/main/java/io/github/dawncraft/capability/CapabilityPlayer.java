package io.github.dawncraft.capability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.entity.player.DrinkStats;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.network.MessagePlayerSpelling;
import io.github.dawncraft.network.MessageSetSlot;
import io.github.dawncraft.network.MessageUpdateMana;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.stats.AchievementLoader;
import io.github.dawncraft.talent.Talent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * IMagic 的具体实现
 *
 * @author QingChenW
 */
public class CapabilityPlayer
{
    public static class Common implements IPlayer
    {
        private EntityPlayer player;
        private float mana;
        private DrinkStats drinkStats = new DrinkStats();
        private EnumSpellAction spellAction;
        private boolean canceled;
        private int currentSkill;
        private SkillStack skillInSpell;
        private int skillInSpellCount;
        private SpellCooldownTracker tracker;
        private SkillInventoryPlayer inventory;
        private Map<Talent, Integer> talents;

        public Common(EntityPlayer player)
        {
            this.player = player;
            this.mana = this.getMaxMana();
            this.spellAction = EnumSpellAction.NONE;
            this.tracker = new SpellCooldownTracker();
            this.inventory = new SkillInventoryPlayer(player);
            this.talents = new HashMap<Talent, Integer>();
        }

        @Override
        public float getMana()
        {
            return this.mana;
        }

        @Override
        public float getMaxMana()
        {
            return (float) this.player.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue();
        }

        @Override
        public void setMana(float mana)
        {
            this.mana = MathHelper.clamp_float(mana, 0.0F, this.getMaxMana());;
        }

        @Override
        public void recover(float recoverAmount)
        {
            recoverAmount = DawnEventFactory.onLivingRecover(this.player, recoverAmount);
            if (recoverAmount <= 0) return;
            float mana = this.getMana();
            
            if (mana > 0.0F)
            {
                this.setMana(mana + recoverAmount);
            }
        }

        @Override
        public boolean shouldRecover()
        {
            return this.getMana() > 0.0F && this.getMana() < this.getMaxMana();
        }

        @Override
        public DrinkStats getDrinkStats()
        {
            return this.drinkStats;
        }
        
        @Override
        public boolean canDrink(boolean ignoreThirst)
        {
            return (!ConfigLoader.isThirstEnabled || ignoreThirst || this.drinkStats.needDrink()) && !this.player.capabilities.disableDamage;
        }

        @Override
        public EnumSpellAction getSpellAction()
        {
            return this.spellAction;
        }

        @Override
        public void setSpellAction(EnumSpellAction action)
        {
            this.spellAction = action;
        }

        @Override
        public boolean isCanceled()
        {
            return this.canceled;
        }

        @Override
        public void cancelSpelling()
        {
            this.canceled = true;
        }

        @Override
        public void setCanceled(boolean isCanceled)
        {
            this.canceled = isCanceled;
        }

        @Override
        public int getSpellIndex()
        {
            return this.currentSkill;
        }

        @Override
        public void setSpellIndex(int index)
        {
            this.currentSkill = index;
        }

        @Override
        public SkillStack getSkillInSpell()
        {
            return this.skillInSpell;
        }

        @Override
        public void setSkillInSpell(SkillStack skillStack)
        {
            if (skillStack != this.skillInSpell)
            {
                int duration = skillStack.getTotalPrepare();
                duration = DawnEventFactory.onSkillSpellStart(this.player, skillStack, duration);
                if (duration <= 0) return;
                this.skillInSpell = skillStack;
                this.skillInSpellCount = duration;
                this.canceled = false;
                this.spellAction = EnumSpellAction.PREPAR;
            }
        }

        @Override
        public void clearSkillInSpell()
        {
            this.spellAction = EnumSpellAction.NONE;
            this.canceled = false;
            this.currentSkill = -1;
            this.skillInSpell = null;
            this.skillInSpellCount = 0;
        }

        @Override
        public int getSkillInSpellCount()
        {
            return this.skillInSpellCount;
        }

        @Override
        public int getSkillInSpellDuration()
        {
            if(this.spellAction == EnumSpellAction.PREPAR)
            {
                return this.skillInSpell.getTotalPrepare() - this.skillInSpellCount;
            }
            else if(this.spellAction == EnumSpellAction.SPELL)
            {
                return this.skillInSpell.getMaxDuration() - this.skillInSpellCount;
            }
            return 0;
        }

        @Override
        public void setSkillInSpellCount(int count)
        {
            this.skillInSpellCount = count;
        }

        @Override
        public SpellCooldownTracker getCooldownTracker()
        {
            return this.tracker;
        }
        
        @Override
        public SkillInventoryPlayer getInventory()
        {
            return this.inventory;
        }

        @Override
        public int getTalentLevel(Talent talent)
        {
            return this.talents.get(talent);
        }

        @Override
        public void setTalent(Talent talent, int level)
        {
            this.talents.put(talent, level);
        }

        @Deprecated
        @Override
        public Set<Talent> getTalents()
        {
            return this.talents.keySet();
        }

        @Override
        public void update()
        {
            if (this.player.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.player.worldObj.getGameRules().getBoolean("naturalRecovery"))
            {
                if (this.shouldRecover() && this.player.ticksExisted % 20 == 0)
                {
                    this.recover(1.0F);
                }
                
                if (ConfigLoader.isThirstEnabled && this.getDrinkStats().needDrink() && this.player.ticksExisted % 10 == 0)
                {
                    this.getDrinkStats().setDrinkLevel(this.getDrinkStats().getDrinkLevel() + 1);
                }
            }

            if(this.skillInSpellCount > 0) this.skillInSpellCount--;
            
            if(this.spellAction != EnumSpellAction.NONE)
            {
                if (this.skillInSpell != null)
                {
                    if(this.spellAction == EnumSpellAction.PREPAR)
                    {
                        EnumSpellAction result = this.getSkillInSpell().onSkillPreparing(this.player.worldObj, this.player, this.getSkillInSpellDuration());
                        if(result != EnumSpellAction.NONE)
                        {
                            if(result == EnumSpellAction.SPELL || this.getSkillInSpellCount() <= 0)
                            {
                                this.setSpellAction(EnumSpellAction.SPELL);
                                this.setMana(this.getMana() - this.getSkillInSpell().getSkillConsume());
                                this.getCooldownTracker().setCooldown(this.getSkillInSpell().getSkill(), this.getSkillInSpell().getTotalCooldown());
                                
                                this.getSkillInSpell().onSkillSpell(this.player.worldObj, this.player);

                                if(this.getSkillInSpell().getMaxDuration() <= 0) this.clearSkillInSpell();
                                else this.setSkillInSpellCount(this.getSkillInSpell().getMaxDuration());
                            }
                        }
                        else
                        {
                            this.clearSkillInSpell();
                        }
                    }
                    else if(this.getSpellAction() == EnumSpellAction.SPELL)
                    {
                        EnumSpellAction result = this.getSkillInSpell().onSkillSpelling(this.player.worldObj, this.player, this.getSkillInSpellDuration());
                        if(result != EnumSpellAction.NONE)
                        {
                            this.skillInSpellCount = DawnEventFactory.onSkillSpellTick(this.player, this.skillInSpell, this.skillInSpellCount);
                            if(this.getSkillInSpellCount() <= 0)
                            {
                                this.inventory.setInventorySlotContents(this.getSpellIndex(), this.getSkillInSpell().onSkillSpellFinish(this.player.worldObj, this.player));
                                if(this.player.isServerWorld())
                                    NetworkLoader.instance.sendTo(new MessageSetSlot(0, this.getSpellIndex(), this.getSkillInSpell()), (EntityPlayerMP) this.player);
                                
                                this.clearSkillInSpell();
                            }
                        }
                        else
                        {
                            this.clearSkillInSpell();
                        }
                    }
                }
                else
                {
                    this.clearSkillInSpell();
                }
            }
            this.setCanceled(false);

            this.getCooldownTracker().tick();

            this.getInventory().decrementAnimations();
        }
    }
    
    public static class Server extends Common
    {
        private EntityPlayerMP player;
        
        private double prevPosX;
        private double prevPosY;
        private double prevPosZ;

        private float lastMana;
        private int lastDrinkLevel;
        private boolean wasThirst;
        private EnumSpellAction lastAction;

        public Server(EntityPlayerMP player)
        {
            super(player);
            this.player = player;
            this.prevPosY = player.posX;
            this.prevPosY = player.posY;
            this.prevPosZ = player.posZ;
        }

        @Override
        public void update()
        {
            super.update();
            
            double x = this.player.posX - this.prevPosX;
            double y = this.player.posY - this.prevPosY;
            double z = this.player.posZ - this.prevPosZ;
            if (x * x + y * y + z * z > 0.1)
            {
                this.cancelSpelling();
            }

            this.getDrinkStats().onUpdate(this.player);
            
            if (this.getMana() != this.lastMana || ConfigLoader.isThirstEnabled && (this.lastDrinkLevel != this.getDrinkStats().getDrinkLevel() || this.getDrinkStats().getSaturationLevel() == 0.0F != this.wasThirst))
            {
                NetworkLoader.instance.sendTo(new MessageUpdateMana(this.getMana(), this.getDrinkStats().getDrinkLevel(), this.getDrinkStats().getSaturationLevel()), this.player);
                this.lastMana = this.getMana();
                this.lastDrinkLevel = this.getDrinkStats().getDrinkLevel();
                this.wasThirst = this.getDrinkStats().getSaturationLevel() == 0.0F;
            }

            if (this.getSpellAction() != this.lastAction)
            {
                NetworkLoader.instance.sendTo(new MessagePlayerSpelling(this.getSpellAction(), this.getSkillInSpellCount()), this.player);
            }
            
            this.prevPosX = this.player.posX;
            this.prevPosY = this.player.posY;
            this.prevPosZ = this.player.posZ;
            
            if (this.player.ticksExisted % 20 * 5 == 0)
            {
                this.checkPortalCreation();
            }
        }
        
        /**
         * Check for can portal create in world.
         * From Benimatic's twilight forest Mod.Thanks.
         * 感谢暮色森林的传送门检查方法
         *
         * @author Benimatic
         */
        private void checkPortalCreation()
        {
            World world = this.player.worldObj;
            if(world != null && this.player != null && world.provider.getDimensionId() == 0)
            {
                List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, this.player.getEntityBoundingBox().expand(ConfigLoader.rangeToCheck, ConfigLoader.rangeToCheck, ConfigLoader.rangeToCheck));

                for(EntityItem entityItem : itemList)
                {
                    if (entityItem.getEntityItem().getItem() == ItemLoader.gerHeart && world.isMaterialInBB(entityItem.getEntityBoundingBox(), Material.water))
                    {
                        int dx = MathHelper.floor_double(entityItem.posX);
                        int dy = MathHelper.floor_double(entityItem.posY);
                        int dz = MathHelper.floor_double(entityItem.posZ);
                        BlockPos pos = new BlockPos(dx, dy, dz);
                        world.setBlockState(pos, BlockLoader.dawnPortal.getDefaultState());
                        world.addWeatherEffect(new EntityLightningBolt(world, dx, dy, dz));
                        this.player.triggerAchievement(AchievementLoader.dawnPortal);
                    }
                }
            }
        }
    }
    
    public static class Storage implements Capability.IStorage<IPlayer>
    {
        @Override
        public NBTBase writeNBT(Capability<IPlayer> capability, IPlayer instance, EnumFacing side)
        {
            NBTTagCompound compound = new NBTTagCompound();
            float mana = instance.getMana();
            compound.setFloat("ManaF", mana);
            compound.setShort("Mana", (short) Math.ceil(mana));

            NBTTagList cooldowns = new NBTTagList();
            instance.getCooldownTracker().writeToNBT(cooldowns);
            compound.setTag("Cooldowns", cooldowns);

            NBTTagList skills = new NBTTagList();
            ((SkillInventoryPlayer) instance.getInventory()).writeToNBT(skills);
            compound.setTag("Inventory", skills);

            NBTTagList talents = new NBTTagList();
            for (Talent talent : instance.getTalents())
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setShort(talent.getUnlocalizedName(), (short) instance.getTalentLevel(talent));
                talents.appendTag(nbt);
            }
            compound.setTag("Talents", talents);

            return compound;
        }

        @Override
        public void readNBT(Capability<IPlayer> capability, IPlayer instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound = (NBTTagCompound) nbt;

            if (compound.hasKey("ManaF", 99))
            {
                instance.setMana(compound.getFloat("ManaF"));
            }
            else
            {
                NBTBase nbtbase = compound.getTag("Mana");

                if (nbtbase == null)
                {
                    instance.setMana(instance.getMaxMana());
                }
                else if (nbtbase.getId() == 5)
                {
                    instance.setMana(((NBTTagFloat) nbtbase).getFloat());
                }
                else if (nbtbase.getId() == 2)
                {
                    instance.setMana(((NBTTagShort)nbtbase).getShort());
                }
            }

            NBTTagList cooldowns = compound.getTagList("Cooldowns", 10);
            instance.getCooldownTracker().readFromNBT(cooldowns);

            NBTTagList skills = compound.getTagList("Inventory", 10);
            ((SkillInventoryPlayer) instance.getInventory()).readFromNBT(skills);

            NBTTagList talents = compound.getTagList("Talents", 10);
            for (int i = 0; i < talents.tagCount(); ++i)
            {
                NBTTagCompound nbt2 = talents.getCompoundTagAt(i);
                //                instance.setTalent();
            }
        }
    }
    
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        private IPlayer player;
        private IStorage<IPlayer> storage;

        public Provider(EntityPlayer player)
        {
            if(player instanceof EntityPlayerMP)
            {
                this.player = new Server((EntityPlayerMP) player);
            }
            else
            {
                this.player = new Common(player);
            }
            this.storage = CapabilityLoader.player.getStorage();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.player.equals(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (this.hasCapability(capability, facing))
            {
                T result = (T) this.player;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) this.storage.writeNBT(CapabilityLoader.player, this.player, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            this.storage.readNBT(CapabilityLoader.player, this.player, null, compound);
        }
    }
}
