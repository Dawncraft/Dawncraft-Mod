package io.github.dawncraft.item;

import io.github.dawncraft.entity.projectile.EntityMagnetBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Magnet ball.
 *
 * @author QingChenW
 */
public class ItemMagnetBall extends Item
{
    public ItemMagnetBall()
    {
        this.setMaxStackSize(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
        if (!world.isRemote)
        {
            BlockPos blockPos = ((WorldServer) world).getChunkProvider().getNearestStructurePos(world, "", new BlockPos(player), false);

            if (blockPos != null)
            {
                EntityMagnetBall entityMagnetBall = new EntityMagnetBall(world, player);
                entityMagnetBall.shoot(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0F, 0.5F);
                world.spawnEntity(entityMagnetBall);
            }
        }
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        player.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
