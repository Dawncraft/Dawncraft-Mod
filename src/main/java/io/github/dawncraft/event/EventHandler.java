package io.github.dawncraft.event;

import java.util.List;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.network.MessagePlayerSpelling;
import io.github.dawncraft.network.MessageSetSlot;
import io.github.dawncraft.network.MessageUpdateMana;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.EnumSpellResult;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.stats.AchievementLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handle some events that related to game logics.
 *
 * @author QingChenW
 */
public class EventHandler
{
    public EventHandler(FMLInitializationEvent event) {}
    
    @SubscribeEvent
    public void playerTickEvent(PlayerTickEvent event)
    {
        if(event.phase == Phase.END)
        {
            EntityPlayer player = event.player;
            World world = player.worldObj;
            
            // 处理魔法
            if(player.hasCapability(CapabilityLoader.magic, null))
            {
                IMagic magic = player.getCapability(CapabilityLoader.magic, null);
                ISkillInventory inventory = magic.getInventory();
                
                if(event.side == Side.SERVER)
                {
                    EntityPlayerMP serverPlayer = (EntityPlayerMP) player;
                    
                    // 更新魔法值
                    if (world.getGameRules().getBoolean("naturalRecovery") && player.getFoodStats().getFoodLevel() >= 16)
                    {
                        if (magic.getMana() < magic.getMaxMana() && player.ticksExisted % 40 == 0)
                        {
                            magic.recover(1.0F);
                            NetworkLoader.instance.sendTo(new MessageUpdateMana(magic.getMana()), serverPlayer);
                        }
                    }
                    
                    // 更新技能施放
                    if(magic.getSpellAction() == EnumSpellResult.SELECT)// 选中技能,检测是否符合施放条件
                    {
                        EnumSpellResult result = magic.getSkillInSpell().onSkillPreparing(world, player, -1);
                        if(!result.isSpellFailed())
                        {
                            magic.setSpellAction(EnumSpellResult.PREPARING);
                            magic.setSkillInSpellCount(magic.getSkillInSpell().getTotalPrepare());
                            magic.setPublicCooldownCount(Skill.getPublicCooldown());
                            NetworkLoader.instance.sendTo(new MessagePlayerSpelling(magic.getSpellAction(), magic.getSkillInSpellCount(), magic.getPublicCooldownCount()), serverPlayer);
                        }
                        else
                        {
                            NetworkLoader.instance.sendTo(new MessagePlayerSpelling(result, 0, magic.getPublicCooldownCount()), serverPlayer);
                            magic.clearSkillInSpell();
                        }
                    }
                    else if(magic.getSpellAction() == EnumSpellResult.PREPARING)// 准备阶段,检测是否能够施法
                    {
                        EnumSpellResult result = magic.getSkillInSpell().onSkillPreparing(world, player, magic.getSkillInSpellDuration());
                        if(!result.isSpellFailed())
                        {
                            if(magic.getSkillInSpellCount() <= 0)
                            {
                                magic.setSpellAction(EnumSpellResult.SPELLING);
                                magic.reduce(magic.getSkillInSpell().getSkillConsume());
                                magic.getSkillInSpell().cooldown = magic.getSkillInSpell().getTotalCooldown();
                                magic.getSkillInSpell().onSkillSpell(world, player);
                                NetworkLoader.instance.sendTo(new MessageUpdateMana(magic.getMana()), serverPlayer);
                                NetworkLoader.instance.sendTo(new MessageSetSlot(0, magic.getSpellIndex(), magic.getSkillInSpell()), serverPlayer);

                                if(magic.getSkillInSpell().getMaxDuration() <= 0) magic.clearSkillInSpell();
                                else magic.setSkillInSpellCount(magic.getSkillInSpell().getMaxDuration());
                                NetworkLoader.instance.sendTo(new MessagePlayerSpelling(magic.getSpellAction(), magic.getSkillInSpellCount(), magic.getPublicCooldownCount()), serverPlayer);
                            }
                        }
                        else
                        {
                            NetworkLoader.instance.sendTo(new MessagePlayerSpelling(result, 0, magic.getPublicCooldownCount()), serverPlayer);
                            magic.clearSkillInSpell();
                        }
                    }
                    else if(magic.getSpellAction() == EnumSpellResult.SPELLING)// 施放阶段,检测是否能持续施法
                    {
                        EnumSpellResult result = magic.getSkillInSpell().onSkillSpelling(world, player, magic.getSkillInSpellDuration());
                        if(!result.isSpellFailed())
                        {
                            if(magic.getSkillInSpellCount() <= 0)
                            {
                                inventory.setInventorySlotContents(magic.getSpellIndex(), magic.getSkillInSpell().onSkillSpellFinish(world, player));
                                NetworkLoader.instance.sendTo(new MessageSetSlot(0, magic.getSpellIndex(), magic.getSkillInSpell()), serverPlayer);
                                
                                magic.clearSkillInSpell();
                                NetworkLoader.instance.sendTo(new MessagePlayerSpelling(magic.getSpellAction(), magic.getSkillInSpellCount(), magic.getPublicCooldownCount()), serverPlayer);
                            }
                        }
                        else
                        {
                            NetworkLoader.instance.sendTo(new MessagePlayerSpelling(result, 0, magic.getPublicCooldownCount()), serverPlayer);
                            magic.clearSkillInSpell();
                        }
                    }
                    else if(magic.getSpellAction() != EnumSpellResult.NONE)
                    {
                        magic.clearSkillInSpell();
                    }
                }
                // 更新技能相关字段
                magic.update();
            }
            
            if(event.side == Side.SERVER)
            {
                // 处理曙光世界传送门
                this.checkForPortalCreation(player, world, 32.0F);
            }
        }
    }
    
    /**
     * Check for can portal create in world.
     * From Benimatic's twilight forest Mod.Thanks.
     * 感谢暮色森林的传送门检查方法
     *
     * @author Benimatic
     * @param player Check portal around player.
     * @param world Check in the world.
     * @param rangeToCheck Check in a range.
     */
    private void checkForPortalCreation(EntityPlayer player, World world, float rangeToCheck)
    {
        if(world != null && player != null && world.provider.getDimensionId() == 0)
        {
            List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.getEntityBoundingBox().expand(rangeToCheck, rangeToCheck, rangeToCheck));
            
            for(EntityItem entityItem : itemList)
            {
                if (entityItem.getEntityItem().getItem() == ItemLoader.gerHeart && world.isMaterialInBB(entityItem.getEntityBoundingBox(), Material.water))
                {
                    int dx = MathHelper.floor_double(entityItem.posX);
                    int dy = MathHelper.floor_double(entityItem.posY);
                    int dz = MathHelper.floor_double(entityItem.posZ);
                    BlockPos pos = new BlockPos(dx, dy , dz);
                    player.worldObj.setBlockState(pos, BlockLoader.dawnPortal.getDefaultState());
                    world.addWeatherEffect(new EntityLightningBolt(world, dx, dy, dz));
                    player.triggerAchievement(AchievementLoader.dawnPortal);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        BlockPos blockpos = event.target.getBlockPos();
        IBlockState blockState = event.world.getBlockState(blockpos);
        Fluid fluid = FluidRegistry.lookupFluidForBlock(blockState.getBlock());
        if (fluid != null && new Integer(0).equals(blockState.getValue(BlockFluidBase.LEVEL)))
        {
            FluidStack fluidStack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
            event.world.setBlockToAir(blockpos);
            event.result = FluidContainerRegistry.fillFluidContainer(fluidStack, event.current);
            event.setResult(Result.ALLOW);
        }
    }
    
    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntitySavage)
        {
            EntitySavage savage = (EntitySavage) event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() == ItemLoader.faeces)
            {
                player.attackEntityFrom(DamageSourceLoader.ger, 20.0F);
                player.worldObj.createExplosion(savage, savage.posX, savage.posY, savage.posZ, 4.0F, false);
                savage.setDead();
            }
        }
    }
}
