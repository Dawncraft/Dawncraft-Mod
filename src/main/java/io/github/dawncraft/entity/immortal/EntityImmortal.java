package io.github.dawncraft.entity.immortal;

import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.capability.IEntityMana;
import io.github.dawncraft.entity.AttributesLoader;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityImmortal extends EntityAgeable implements IEntityMana
{
    public static int MANA_ID = 19;

    public EntityImmortal(World worldIn)
    {
        super(worldIn);
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(AttributesLoader.maxMana);
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataWatcher().addObject(MANA_ID, Float.valueOf(0));
    }

    @Override
    public float getMana()
    {
        return this.getDataWatcher().getWatchableObjectFloat(MANA_ID);
    }
    
    @Override
    public final float getMaxMana()
    {
        return (float) this.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue();
    }

    @Override
    public void setMana(float mana)
    {
        this.getDataWatcher().updateObject(MANA_ID, Float.valueOf(MathHelper.clamp_float(mana, 0.0F, this.getMana())));
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
