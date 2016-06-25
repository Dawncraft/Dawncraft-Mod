package WdawningStudio.DawnW.science.common;

import WdawningStudio.DawnW.science.achievement.AchievementLoader;
import WdawningStudio.DawnW.science.entity.EntitySavage;
import WdawningStudio.DawnW.science.item.ItemLoader;
import WdawningStudio.DawnW.science.potion.PotionLoader;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventLoader
{
	//register
    public EventLoader()
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    //event
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
    public void onLivingHurt(LivingHurtEvent event)
    {
        if (event.source.getDamageType().equals("fall"))
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
                player.attackEntityFrom((new DamageSource("byGer")).setDifficultyScaled().setExplosion(), 12.0F);
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
    }
    
    //end
}