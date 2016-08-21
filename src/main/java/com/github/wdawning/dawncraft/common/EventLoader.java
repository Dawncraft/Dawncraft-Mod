package com.github.wdawning.dawncraft.common;

import com.github.wdawning.dawncraft.achievement.AchievementLoader;
import com.github.wdawning.dawncraft.client.KeyLoader;
import com.github.wdawning.dawncraft.enchantment.EnchantmentLoader;
import com.github.wdawning.dawncraft.entity.EntitySavage;
import com.github.wdawning.dawncraft.extend.ExtendedPlayer;
import com.github.wdawning.dawncraft.item.ItemLoader;
import com.github.wdawning.dawncraft.network.MessageMagic;
import com.github.wdawning.dawncraft.network.NetworkLoader;
import com.github.wdawning.dawncraft.potion.PotionLoader;
import com.github.wdawning.dawncraft.gui.GuiMagicBook;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventLoader
{
	public float defaultFov;
	
	//register
    public EventLoader()
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    //event
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	
        if (KeyLoader.aim.isPressed())
        {
        	if(mc.gameSettings.fovSetting != 10.0F)
        	{
        		defaultFov = mc.gameSettings.fovSetting;
        		mc.gameSettings.fovSetting = 10.0F;
        	}
        	else
        	{
        		mc.gameSettings.fovSetting = defaultFov;
        	}
        	
            EntityPlayerSP player = mc.thePlayer;
            player.addChatMessage(new ChatComponentTranslation("chat.dawncraft.zoom"));
        }
        
        if (KeyLoader.magic.isPressed())
        {  
            Minecraft.getMinecraft().displayGuiScreen(new GuiMagicBook());
        }
    }
    
    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
    	if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null)
    	{
    		ExtendedPlayer.register((EntityPlayer) event.entity);
    	}
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (ExtendedPlayer.get(player) != null)
            {
            	int amount = ExtendedPlayer.get(player).getMana();
                NetworkLoader.instance.sendTo(new MessageMagic(amount), (EntityPlayerMP) player);
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
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null)
        {
            ItemStack itemStack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireBurn.effectId, itemStack) > 0
                    && itemStack.getItem() != Items.shears)
            {
                for (ItemStack stack : event.drops)
                {
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null)
                    {
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(event.drops.indexOf(stack), newStack);
                    }
                    else if (stack != null)
                    {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b && (block.isFlammable(event.world, event.pos, EnumFacing.DOWN)
                                || block.isFlammable(event.world, event.pos, EnumFacing.EAST)
                                || block.isFlammable(event.world, event.pos, EnumFacing.NORTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.SOUTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.UP)
                                || block.isFlammable(event.world, event.pos, EnumFacing.WEST)))
                        {
                            event.drops.remove(stack);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        if (event.source.getDamageType().equals("mob"))
        {
            PotionEffect effect = event.entityLiving.getActivePotionEffect(PotionLoader.potionGerPower);
            if (effect != null)
            {
                if (effect.getAmplifier() == 0)
                {
                    event.ammount /= 2;
                }
                else
                {
                    event.ammount = 0;
                }
            }
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
            if (stack != null && (stack.getItem() == ItemLoader.faeces))
            {
                player.attackEntityFrom((new DamageSource("byGer")).setDifficultyScaled().setExplosion(), 20.0F);
                player.worldObj.createExplosion(savage, savage.posX, savage.posY, savage.posZ, 4.0F, false);
                savage.setDead();
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        if (event.entityLiving instanceof EntityPlayer && event.source.getDamageType().equals("byGer"))
        {
            ((EntityPlayer) event.entityLiving).triggerAchievement(AchievementLoader.Ger);
        }
        
        
        if(!event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
            if (ExtendedPlayer.get((EntityPlayer) event.entityLiving) != null)
            {
            	int amount = 20;
            	ExtendedPlayer.get((EntityPlayer) event.entityLiving).setMana(amount);
                NetworkLoader.instance.sendTo(new MessageMagic(amount), player);
            }
        }
    }
    
    @SubscribeEvent
    public void onClonePlayer(PlayerEvent.Clone event)
    {
        if(event.wasDeath)
        {
        	  NBTTagCompound compound = new NBTTagCompound();
              ExtendedPlayer.get(event.original).saveNBTData(compound);
              ExtendedPlayer.get(event.entityPlayer).loadNBTData(compound);
        }
    }
    
    //end
}