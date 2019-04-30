package io.github.dawncraft.entity.player;

import io.github.dawncraft.api.item.ItemDrink;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DrinkStats
{
    /** The player's drink level. */
    private int drinkLevel = 20;
    /** The player's drink saturation. */
    private float drinkSaturationLevel = 5.0F;
    /** The player's drink exhaustion. */
    private float drinkExhaustionLevel;
    /** The player's drink timer value. */
    private int drinkTimer;
    private int prevDrinkLevel = 20;

    /**
     * Add drink stats.
     */
    public void addStats(int drinkLevel, float drinkSaturationModifier)
    {
        if (ConfigLoader.isThirstEnabled)
        {
            this.drinkLevel = Math.min(drinkLevel + this.drinkLevel, 20);
            this.drinkSaturationLevel = Math.min(this.drinkSaturationLevel + drinkLevel * drinkSaturationModifier * 2.0F, this.drinkLevel);
        }
    }

    public void addStats(ItemDrink item, ItemStack stack)
    {
        this.addStats(item.getWaterAmount(stack), item.getSaturationModifier(stack));
    }

    /**
     * Handles the drink game logic.
     */
    public void onUpdate(EntityPlayer player)
    {
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
        EnumDifficulty difficulty = player.worldObj.getDifficulty();

        if (ConfigLoader.isThirstEnabled)
        {
            this.prevDrinkLevel = this.drinkLevel;

            if (this.drinkExhaustionLevel > 4.0F)
            {
                this.drinkExhaustionLevel -= 4.0F;

                if (this.drinkSaturationLevel > 0.0F)
                {
                    this.drinkSaturationLevel = Math.max(this.drinkSaturationLevel - 1.0F, 0.0F);
                }
                else if (difficulty != EnumDifficulty.PEACEFUL)
                {
                    this.drinkLevel = Math.max(this.drinkLevel - 1, 0);
                }
            }

            if (player.worldObj.getGameRules().getBoolean("naturalRecovery") && this.drinkLevel >= 8 && playerMagic.shouldRecover())
            {
                ++this.drinkTimer;

                if (this.drinkTimer >= 30)
                {
                    playerMagic.recover(1.0F);
                    this.drinkTimer = 0;
                }
            }
            else if (this.drinkLevel <= 0)
            {
                ++this.drinkTimer;

                if (this.drinkTimer >= 60)
                {
                    if (player.getHealth() > 10.0F || difficulty == EnumDifficulty.HARD || player.getHealth() > 1.0F && difficulty == EnumDifficulty.NORMAL)
                    {
                        player.attackEntityFrom(DamageSourceLoader.thirst, 1.0F);
                    }

                    this.drinkTimer = 0;
                }
            }
            else
            {
                this.drinkTimer = 0;
            }
        }
        else
        {
            if (player.worldObj.getGameRules().getBoolean("naturalRecovery") && player.getFoodStats().getFoodLevel() >= 12)
            {
                if (playerMagic.shouldRecover() && player.ticksExisted % 30 == 0)
                {
                    playerMagic.recover(1.0F);
                }
            }
        }
    }

    /**
     * Reads the drink data for the player.
     */
    public void readNBT(NBTTagCompound nbt)
    {
        if (nbt.hasKey("drinkLevel", 99))
        {
            this.drinkLevel = nbt.getInteger("drinkLevel");
            this.drinkTimer = nbt.getInteger("drinkTickTimer");
            this.drinkSaturationLevel = nbt.getFloat("drinkSaturationLevel");
            this.drinkExhaustionLevel = nbt.getFloat("drinkExhaustionLevel");
        }
    }

    /**
     * Writes the drink data for the player.
     */
    public void writeNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("drinkLevel", this.drinkLevel);
        nbt.setInteger("drinkTickTimer", this.drinkTimer);
        nbt.setFloat("drinkSaturationLevel", this.drinkSaturationLevel);
        nbt.setFloat("drinkExhaustionLevel", this.drinkExhaustionLevel);
    }

    /**
     * Get the player's drink level.
     */
    public int getDrinkLevel()
    {
        return this.drinkLevel;
    }

    @SideOnly(Side.CLIENT)
    public int getPrevDrinkLevel()
    {
        return this.prevDrinkLevel;
    }

    /**
     * Get whether the player must drink water.
     */
    public boolean needDrink()
    {
        return this.drinkLevel < 20;
    }

    /**
     * adds input to drinkExhaustionLevel to a max of 40
     */
    public void addExhaustion(float drinkExhaustionLevel)
    {
        this.drinkExhaustionLevel = Math.min(this.drinkExhaustionLevel + drinkExhaustionLevel, 40.0F);
    }

    /**
     * Get the player's drink saturation level.
     */
    public float getSaturationLevel()
    {
        return this.drinkSaturationLevel;
    }

    public void setDrinkLevel(int drinkLevel)
    {
        this.drinkLevel = drinkLevel;
    }

    public void setDrinkSaturationLevel(float drinkSaturationLevel)
    {
        this.drinkSaturationLevel = drinkSaturationLevel;
    }
}