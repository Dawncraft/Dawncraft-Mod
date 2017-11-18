package io.github.dawncraft.event;

import java.util.List;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.entity.EntitySavage;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.stats.AchievementLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * @author QingChenW
 *
 */
public class EventHandler
{
    public EventHandler(FMLInitializationEvent event) {}
    
    @SubscribeEvent
    public void playerTickEvent(PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        World world = player.worldObj;

        this.checkForPortalCreation(player, world, 32.0F);
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
