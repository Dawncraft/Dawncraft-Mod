package io.github.dawncraft.api.item;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerThirst;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Like ItemFood, but it is used to be drunk.
 *
 * @author QingChenW
 */
public class ItemDrink extends Item
{
    /** Number of ticks to run while 'EnumAction'ing until result. */
    public final int itemUseDuration;
    /** The amount this drink item waters the player. */
    private final int waterAmount;
    private final float saturationModifier;
    /** If this field is true, the food can be consumed even if the player don't need to eat. */
    private boolean alwaysDrinkable;
    /** represents the potion effect that will occur upon eating this food. Set by setPotionEffect */
    private PotionEffect potion;
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
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        IPlayerThirst playerThirst = player.getCapability(CapabilityLoader.PLAYER_THIRST, null);

        if (playerThirst.canDrink(this.alwaysDrinkable))
        {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if (!player.capabilities.isCreativeMode)
                stack.shrink(1);
            IPlayerThirst playerThirst = entityLiving.getCapability(CapabilityLoader.PLAYER_THIRST, null);
            if (playerThirst.getDrinkStats() != null)
                playerThirst.getDrinkStats().addStats(this, stack);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            this.onDrinkDrunk(stack, world, player);
            player.addStat(StatList.getObjectUseStats(this));

            if (player instanceof EntityPlayerMP)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
            }

            if (!player.capabilities.isCreativeMode)
            {
                if (stack.isEmpty())
                {
                    return new ItemStack(ItemInit.TUMBLER);
                }
                player.inventory.addItemStackToInventory(new ItemStack(ItemInit.TUMBLER));
            }
        }
        return stack;
    }

    protected void onDrinkDrunk(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && this.potion != null && world.rand.nextFloat() < this.potionEffectProbability)
        {
            player.addPotionEffect(new PotionEffect(this.potion));
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
     * Set the field 'alwaysEdible' to true, and make the drink drinkable even if the player don't need to drink.
     */
    public ItemDrink setAlwaysDrinkable()
    {
        this.alwaysDrinkable = true;
        return this;
    }

    public ItemDrink setPotionEffect(PotionEffect effect, float probability)
    {
        this.potion = effect;
        this.potionEffectProbability = probability;
        return this;
    }
}
