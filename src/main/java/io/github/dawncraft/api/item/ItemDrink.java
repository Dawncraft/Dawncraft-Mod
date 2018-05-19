package io.github.dawncraft.api.item;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemDrink extends Item
{
    /** Number of ticks to run while 'EnumAction'ing until result. */
    public final int itemUseDuration;
    /** The amount this drink item waters the player. */
    private final int waterAmount;
    private final float saturationModifier;
    /** If this field is true, the food can be consumed even if the player don't need to eat. */
    private boolean alwaysDrinkable;
    /** represents the potion effect that will occurr upon eating this food. Set by setPotionEffect */
    private int potionId;
    /** set by setPotionEffect */
    private int potionDuration;
    /** set by setPotionEffect */
    private int potionAmplifier;
    /** probably of the set potion effect occurring */
    private float potionEffectProbability;

    public ItemDrink(int amount, float saturation)
    {
        this.setMaxStackSize(1);
        this.itemUseDuration = 32;
        this.waterAmount = amount;
        this.saturationModifier = saturation;
        this.setCreativeTab(CreativeTabsLoader.tabCuisine);
    }
    
    public ItemDrink(int amount)
    {
        this(amount, 0.6F);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (true/*player.canEat(this.alwaysDrinkable)*/)
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        
        return stack;
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        //player.getFoodStats().addStats(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onDrinkDrunk(stack, world, player);
        player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return new ItemStack(Items.bowl);
    }
    
    protected void onDrinkDrunk(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && this.potionId > 0 && world.rand.nextFloat() < this.potionEffectProbability)
        {
            player.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    
    public int getWaterAmount(ItemStack stack)
    {
        return this.waterAmount;
    }

    public float getSaturationModifier(ItemStack stack)
    {
        return this.saturationModifier;
    }

    /**
     * sets a potion effect on the item. Args: int potionId, int duration (will be multiplied by 20), int amplifier,
     * float probability of effect happening
     */
    public ItemDrink setPotionEffect(int id, int duration, int amplifier, float probability)
    {
        this.potionId = id;
        this.potionDuration = duration;
        this.potionAmplifier = amplifier;
        this.potionEffectProbability = probability;
        return this;
    }
    
    /**
     * Set the field 'alwaysEdible' to true, and make the drink drinkable even if the player don't need to drink.
     */
    public ItemDrink setAlwaysDrinkable()
    {
        this.alwaysDrinkable = true;
        return this;
    }
}
