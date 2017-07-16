package io.github.dawncraft.event.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

/**
 * BulletNockEvent is fired when a player begins using a flan.<br>
 * This event is fired whenever a player begins using a flan in
 * ItemFlan#onItemRightClick(ItemStack, World, EntityPlayer).<br>
 * <br>
 * {@link #result} contains the resulting ItemStack due to the use of the flan. <br>
 * <br>
 * This event is {@link Cancelable}.<br>
 * If this event is canceled, the player does not begin using the flan.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class BulletNockEvent extends PlayerEvent
{
    public ItemStack result;
    
    public BulletNockEvent(EntityPlayer player, ItemStack result)
    {
        super(player);
        this.result = result;
    }
}
