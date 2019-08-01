package io.github.dawncraft.entity.immortal;

import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.capability.IEntityMana;
import io.github.dawncraft.entity.AttributesLoader;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityImmortal extends EntityAgeable implements IEntityMana
{
    private static final DataParameter<Float> MANA = EntityDataManager.<Float>createKey(EntityImmortal.class, DataSerializers.FLOAT);

    public EntityImmortal(World world)
    {
        super(world);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(AttributesLoader.MAX_MANA);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(MANA, Float.valueOf(1.0F));
    }

    @Override
    public float getMana()
    {
        return this.dataManager.get(MANA).floatValue();
    }

    @Override
    public final float getMaxMana()
    {
        return (float) this.getEntityAttribute(AttributesLoader.MAX_MANA).getAttributeValue();
    }

    @Override
    public void setMana(float mana)
    {
        this.dataManager.set(MANA, MathHelper.clamp(mana, 0.0F, this.getMaxHealth()));
    }

    @Override
    public void recover(float recoverAmount)
    {
        recoverAmount = DawnEventFactory.onLivingRecover(this, recoverAmount);
        if (recoverAmount <= 0) return;
        float mana = this.getMana();
        if (mana < 0.0F) mana = 0.0F;
        this.setMana(mana + recoverAmount);
    }

    @Override
    public boolean shouldRecover()
    {
        return this.getMana() < this.getMaxMana();
    }
}
