package io.github.dawncraft.capability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.container.SkillContainerPlayer;
import io.github.dawncraft.container.SkillSlotLearning;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.network.MessageActionMessage;
import io.github.dawncraft.network.MessagePlayerSpelling;
import io.github.dawncraft.network.MessageSetSkillSlot;
import io.github.dawncraft.network.MessageUpdateMana;
import io.github.dawncraft.network.MessageWindowSkills;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.server.SpellCooldownTrackerServer;
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
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * The implementation of IPlayerMagic.
 *
 * @author QingChenW
 */
public class CapabilityMagic
{
    public static class Common implements IPlayerMagic
    {
        private EntityPlayer player;
        private float mana;
        private EnumSpellAction spellAction;
        private SkillStack skillInSpell;
        private int skillInSpellCount;
        private SpellCooldownTracker tracker;
        private SkillInventoryPlayer skillInventory;
        private SkillContainer skillInventoryContainer;
        private Map<Talent, Integer> talents;

        public Common(EntityPlayer player)
        {
            this.player = player;
            this.mana = this.getMaxMana();
            this.spellAction = EnumSpellAction.NONE;
            this.tracker = player.getEntityWorld().isRemote ? new SpellCooldownTracker() : null;
            // FIXME EntityPlayerSP的isServerWorld()返回true?
            this.skillInventory = new SkillInventoryPlayer(player);
            this.skillInventoryContainer = new SkillContainerPlayer(this.skillInventory, player.isServerWorld(), player);
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
            this.mana = MathHelper.clamp_float(mana, 0.0F, this.getMaxMana());
        }

        @Override
        public void recover(float recoverAmount)
        {
            recoverAmount = DawnEventFactory.onLivingRecover(this.player, recoverAmount);
            if (recoverAmount <= 0) return;
            float mana = this.getMana();
            
            if (mana >= 0.0F)
            {
                this.setMana(mana + recoverAmount);
            }
        }

        @Override
        public boolean shouldRecover()
        {
            return this.getMana() < this.getMaxMana();
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
        public SkillStack getSkillInSpell()
        {
            return this.skillInSpell;
        }

        @Override
        public void setSkillInSpell(EnumSpellAction action, SkillStack skillStack, int duration)
        {
            this.spellAction = action;
            this.skillInSpell = skillStack;
            this.skillInSpellCount = duration;
            this.updateSpellingProgress();
        }

        @Override
        public void clearSkillInSpell()
        {
            this.spellAction = EnumSpellAction.NONE;
            this.skillInSpell = null;
            this.skillInSpellCount = 0;
            this.updateSpellingProgress();
        }

        @Override
        public boolean initSkillInSpell(SkillStack skillStack)
        {
            if (skillStack != this.getSkillInSpell())
            {
                if (skillStack.onSkillPreparing(this.player.getEntityWorld(), this.player, 0) != EnumSpellAction.NONE)
                {
                    int duration = skillStack.getTotalPrepare();
                    duration = DawnEventFactory.onSkillPrepareStart(this.player, skillStack, duration);
                    if (duration <= 0) return false;
                    this.setSkillInSpell(EnumSpellAction.PREPARE, skillStack, duration);
                    return true;
                }
                else
                {
                    this.clearSkillInSpell();
                    return false;
                }
            }
            return false;
        }

        @Override
        public int getSkillInSpellCount()
        {
            return this.skillInSpellCount;
        }

        @Override
        public int getSkillInSpellDuration()
        {
            if (this.spellAction == EnumSpellAction.PREPARE)
            {
                return this.skillInSpell.getTotalPrepare() - this.skillInSpellCount;
            }
            else if (this.spellAction == EnumSpellAction.SPELL)
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

        public void stopSpellingSkill()
        {
            if (this.skillInSpell != null)
            {
                boolean flag = true;
                if (this.spellAction == EnumSpellAction.PREPARE)
                {
                    flag = !DawnEventFactory.onSkillPrepareStop(this.player, this.skillInSpell, this.skillInSpellCount);

                }
                else if (this.spellAction == EnumSpellAction.SPELL)
                {
                    flag = !DawnEventFactory.onSkillSpellStop(this.player, this.skillInSpell, this.skillInSpellCount);
                }
                if (flag) this.skillInSpell.onPlayerStoppedSpelling(this.player.getEntityWorld(), this.player, this.skillInSpellCount);
            }
            this.clearSkillInSpell();
        }

        public void onSkillSpellFinish()
        {
            if (this.skillInSpell != null)
            {
                int level = this.skillInSpell.getSkillLevel();
                SkillStack skillStack = this.skillInSpell.onSkillSpellFinish(this.player.getEntityWorld(), this.player);

                skillStack = DawnEventFactory.onSkillSpellFinish(this.player, this.skillInSpell, this.skillInSpellCount, skillStack);
                if (skillStack != this.skillInSpell || skillStack != null && skillStack.getSkillLevel() != level)
                {
                    for (int i = 0; i < this.skillInventory.getInventorySize(); i++)
                    {
                        if (this.skillInventory.getStackInSlot(i) == this.skillInSpell)
                        {
                            this.skillInventory.setInventorySlot(i, skillStack);
                            break;
                        }
                    }
                }

                this.sendActionBarMessage(new ChatComponentTranslation(this.spellAction.getUnlocalizedName(), skillStack.getDisplayName()), EnumChatFormatting.GREEN);
                this.clearSkillInSpell();
            }
        }
        
        @Override
        public void cancelSpellingSkill()
        {
            this.stopSpellingSkill();
            this.sendCancelSpellReason(new ChatComponentTranslation("gui.skill.cancel"), true);
        }
        
        @Override
        public SpellCooldownTracker getCooldownTracker()
        {
            return this.tracker;
        }
        
        @Override
        public SkillInventoryPlayer getSkillInventory()
        {
            return this.skillInventory;
        }
        
        @Override
        public SkillContainer getSkillInventoryContainer()
        {
            return this.skillInventoryContainer;
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
                if (this.shouldRecover() && this.player.ticksExisted % 15 == 0)
                {
                    this.recover(1.0F);
                }
                
                IPlayerThirst playerThirst = this.player.getCapability(CapabilityLoader.playerThirst, null);
                
                if (ConfigLoader.isThirstEnabled && playerThirst.getDrinkStats().needDrink() && this.player.ticksExisted % 10 == 0)
                {
                    playerThirst.getDrinkStats().setDrinkLevel(playerThirst.getDrinkStats().getDrinkLevel() + 1);
                }
            }

            if (this.skillInSpellCount > 0) --this.skillInSpellCount;
            
            if (!this.player.getEntityWorld().isRemote && this.spellAction != EnumSpellAction.NONE)
            {
                if (this.skillInSpell != null)
                {
                    if (this.spellAction == EnumSpellAction.PREPARE)
                    {
                        EnumSpellAction result = this.skillInSpell.onSkillPreparing(this.player.getEntityWorld(), this.player, this.getSkillInSpellDuration());
                        if (result == EnumSpellAction.PREPARE || result == EnumSpellAction.SPELL)
                        {
                            this.skillInSpellCount = DawnEventFactory.onSkillPrepareTick(this.player, this.skillInSpell, this.skillInSpellCount);
                            if (result == EnumSpellAction.SPELL || this.skillInSpellCount <= 0)
                            {
                                this.setMana(this.getMana() - this.skillInSpell.getSkillConsume());
                                this.getCooldownTracker().setGlobalCooldown(this.getCooldownTracker().getTotalGlobalCooldown());
                                this.getCooldownTracker().setCooldown(this.skillInSpell.getSkill(), this.skillInSpell.getTotalCooldown());

                                this.setSpellAction(EnumSpellAction.SPELL);
                                this.setSkillInSpellCount(this.skillInSpell.getMaxDuration());
                                
                                DawnEventFactory.onSkillSpellStart(this.player, this.skillInSpell, this.getSkillInSpellDuration());
                                this.skillInSpell.onSkillSpell(this.player.getEntityWorld(), this.player);

                                if (this.skillInSpellCount > 0)
                                {
                                    this.updateSpellingProgress();
                                }
                                else
                                {
                                    this.onSkillSpellFinish();
                                }
                            }
                        }
                        else
                        {
                            this.stopSpellingSkill();
                        }
                    }
                    else if (this.getSpellAction() == EnumSpellAction.SPELL)
                    {
                        EnumSpellAction result = this.skillInSpell.onSkillSpelling(this.player.getEntityWorld(), this.player, this.getSkillInSpellDuration());
                        if (result == EnumSpellAction.SPELL)
                        {
                            this.skillInSpellCount = DawnEventFactory.onSkillSpellTick(this.player, this.skillInSpell, this.skillInSpellCount);
                            if (this.getSkillInSpellCount() <= 0)
                            {
                                this.onSkillSpellFinish();
                            }
                        }
                        else
                        {
                            this.stopSpellingSkill();
                        }
                    }
                }
                else
                {
                    this.clearSkillInSpell();
                }
            }

            this.getSkillInventory().decrementAnimations();

            this.getCooldownTracker().tick();
        }
        
        @Override
        public IPlayerMagic cloneCapability(IPlayerMagic oldMagic, boolean wasDeath)
        {
            if (!wasDeath)
            {
                this.setMana(oldMagic.getMana());
            }
            this.getSkillInventory().copyInventory(oldMagic.getSkillInventory());
            return this;
        }
        
        public void updateSpellingProgress() {}
        
        @Override
        public void sendCancelSpellReason(IChatComponent reason, boolean useActionBar) {}
        
        @Override
        public void sendOverlayMessage(IChatComponent chatComponent) {}
        
        @Override
        public void sendActionBarMessage(IChatComponent chatComponent, EnumChatFormatting foregroundColor) {}

        @Override
        public void updateLearningInventory(SkillContainer containerToSend, List<SkillStack> skillsList) {}
        
        @Override
        public void sendSlotContents(SkillContainer containerToSend, int slotId, SkillStack stack) {}
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

        public Server(EntityPlayerMP player)
        {
            super(player);
            this.player = player;
            super.tracker = new SpellCooldownTrackerServer(player);
            this.prevPosY = player.posX;
            this.prevPosY = player.posY;
            this.prevPosZ = player.posZ;
        }

        @Override
        public void update()
        {
            super.update();
            
            if (this.player.openContainer != this.getSkillInventoryContainer())
                this.getSkillInventoryContainer().detectAndSendChanges();
            
            double x = this.player.posX - this.prevPosX;
            double y = this.player.posY - this.prevPosY;
            double z = this.player.posZ - this.prevPosZ;
            if (this.getSpellAction() != EnumSpellAction.NONE && x * x + y * y + z * z > 0.001F)
            {
                this.cancelSpellingSkill();
            }

            IPlayerThirst playerThirst = this.player.getCapability(CapabilityLoader.playerThirst, null);
            playerThirst.getDrinkStats().onUpdate(this.player);
            
            if (this.getMana() != this.lastMana || ConfigLoader.isThirstEnabled && (this.lastDrinkLevel != playerThirst.getDrinkStats().getDrinkLevel() || playerThirst.getDrinkStats().getSaturationLevel() == 0.0F != this.wasThirst))
            {
                NetworkLoader.instance.sendTo(new MessageUpdateMana(this.getMana(), playerThirst.getDrinkStats().getDrinkLevel(), playerThirst.getDrinkStats().getSaturationLevel()), this.player);
                this.lastMana = this.getMana();
                this.lastDrinkLevel = playerThirst.getDrinkStats().getDrinkLevel();
                this.wasThirst = playerThirst.getDrinkStats().getSaturationLevel() == 0.0F;
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

        @Override
        public IPlayerMagic cloneCapability(IPlayerMagic oldMagic, boolean wasDeath)
        {
            super.cloneCapability(oldMagic, wasDeath);
            this.lastMana = -1.0F;
            this.lastDrinkLevel = -1;
            return this;
        }
        
        @Override
        public void updateSpellingProgress()
        {
            NetworkLoader.instance.sendTo(new MessagePlayerSpelling(this.getSpellAction(), this.getSkillInSpellCount()), this.player);
        }
        
        @Override
        public void sendCancelSpellReason(IChatComponent reason, boolean useActionBar)
        {
            if (!useActionBar)
            {
                this.sendOverlayMessage(reason);
            }
            else
            {
                this.sendActionBarMessage(reason, EnumChatFormatting.RED);
            }
        }

        @Override
        public void sendOverlayMessage(IChatComponent chatComponent)
        {
            this.player.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent, (byte) 2));
        }
        
        @Override
        public void sendActionBarMessage(IChatComponent chatComponent, EnumChatFormatting foregroundColor)
        {
            NetworkLoader.instance.sendTo(new MessageActionMessage(chatComponent, foregroundColor), this.player);
        }

        @Override
        public void updateLearningInventory(SkillContainer containerToSend, List<SkillStack> skillsList)
        {
            NetworkLoader.instance.sendTo(new MessageWindowSkills(containerToSend.windowId, skillsList), this.player);
            NetworkLoader.instance.sendTo(new MessageSetSkillSlot(-1, -1, this.getSkillInventory().getSkillStack()), this.player);
        }
        
        @Override
        public void sendSlotContents(SkillContainer containerToSend, int slotId, SkillStack stack)
        {
            if (!(containerToSend.getSkillSlot(slotId) instanceof SkillSlotLearning))
            {
                NetworkLoader.instance.sendTo(new MessageSetSkillSlot(containerToSend.windowId, slotId, stack), this.player);
            }
        }
    }
    
    public static class Storage implements Capability.IStorage<IPlayerMagic>
    {
        @Override
        public NBTBase writeNBT(Capability<IPlayerMagic> capability, IPlayerMagic instance, EnumFacing facing)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            float mana = instance.getMana();
            tagCompound.setFloat("ManaF", mana);
            tagCompound.setShort("Mana", (short) Math.ceil(mana));

            NBTTagList skills = new NBTTagList();
            ((SkillInventoryPlayer) instance.getSkillInventory()).writeToNBT(skills);
            tagCompound.setTag("Inventory", skills);
            
            NBTTagList cooldowns = new NBTTagList();
            instance.getCooldownTracker().writeToNBT(cooldowns);
            tagCompound.setTag("Cooldowns", cooldowns);

            NBTTagList talents = new NBTTagList();
            for (Talent talent : instance.getTalents())
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setShort(talent.getUnlocalizedName(), (short) instance.getTalentLevel(talent));
                talents.appendTag(nbt);
            }
            tagCompound.setTag("Talents", talents);

            return tagCompound;
        }

        @Override
        public void readNBT(Capability<IPlayerMagic> capability, IPlayerMagic instance, EnumFacing facing, NBTBase nbtBase)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) nbtBase;

            if (tagCompound.hasKey("ManaF", 99))
            {
                instance.setMana(tagCompound.getFloat("ManaF"));
            }
            else
            {
                NBTBase mana = tagCompound.getTag("Mana");

                if (mana == null)
                {
                    instance.setMana(instance.getMaxMana());
                }
                else if (mana.getId() == 5)
                {
                    instance.setMana(((NBTTagFloat) mana).getFloat());
                }
                else if (mana.getId() == 2)
                {
                    instance.setMana(((NBTTagShort) mana).getShort());
                }
            }

            NBTTagList cooldowns = tagCompound.getTagList("Cooldowns", 10);
            instance.getCooldownTracker().readFromNBT(cooldowns);

            NBTTagList skills = tagCompound.getTagList("Inventory", 10);
            instance.getSkillInventory().readFromNBT(skills);

            NBTTagList talents = tagCompound.getTagList("Talents", 10);
            for (int i = 0; i < talents.tagCount(); ++i)
            {
                NBTTagCompound nbt2 = talents.getCompoundTagAt(i);
                //                instance.setTalent();
            }
        }
    }
    
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        private IPlayerMagic instance;
        private IStorage<IPlayerMagic> storage;

        public Provider(EntityPlayer player)
        {
            if (player instanceof EntityPlayerMP)
            {
                this.instance = new Server((EntityPlayerMP) player);
            }
            else
            {
                this.instance = new Common(player);
            }
            this.storage = CapabilityLoader.playerMagic.getStorage();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.playerMagic.equals(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (this.hasCapability(capability, facing))
            {
                T result = (T) this.instance;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) this.storage.writeNBT(CapabilityLoader.playerMagic, this.instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound tagCompound)
        {
            this.storage.readNBT(CapabilityLoader.playerMagic, this.instance, null, tagCompound);
        }
    }
}
