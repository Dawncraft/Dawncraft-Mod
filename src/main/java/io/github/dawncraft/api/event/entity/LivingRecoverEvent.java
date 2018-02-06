package io.github.dawncraft.api.event.entity;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;

/**
 * LivingRecoverEvent is fired when an Entity is set to be recovered. <br>
 * This event is fired whenever an Entity is recovered in EntityLivingBase#heal(float)<br>
 * <br>
 * This event is fired via the {@link ForgeHooks#onLivingHeal(EntityLivingBase, float)}.<br>
 * <br>
 * {@link #amount} contains the amount of healing done to the Entity that was healed. <br>
 * <br>
 * This event is {@link Cancelable}.<br>
 * If this event is canceled, the Entity is not healed.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class LivingRecoverEvent extends LivingEvent
{
    public float amount;
    public LivingRecoverEvent(EntityLivingBase entity, float amount)
    {
        super(entity);
        this.amount = amount;
    }
}