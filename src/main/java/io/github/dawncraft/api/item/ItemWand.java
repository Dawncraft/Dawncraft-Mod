package io.github.dawncraft.api.item;

import java.util.UUID;

import com.google.common.collect.Multimap;

import io.github.dawncraft.entity.AttributesLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.AttributeModifierOperation;
import net.minecraftforge.oredict.OreDictionary;

/**
 * A new weapon that can speed up spelling skills.
 *
 * @author QingChenW
 */
public class ItemWand extends Item
{
    protected static final UUID SPELL_SPEED_MODIFIER = UUID.fromString("FB233E8C-4957-4885-B01B-BB8E9785ACB5");

    private final Item.ToolMaterial material;
    private float spellSpeed;

    public ItemWand(Item.ToolMaterial material, float spellSpeed)
    {
        this.material = material;
        this.setFull3D();
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.spellSpeed = spellSpeed;
    }

    /**
     * Returns the percent of speed this wand can provide.
     */
    public float getSpellSpeed()
    {
        return this.spellSpeed;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(2, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (state.getBlockHardness(world, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }

    @Override
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    public String getToolMaterialName()
    {
        return this.material.toString();
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = this.material.getRepairItemStack();
        if (mat != null && OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.material.getAttackDamage(), AttributeModifierOperation.ADD));
        multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.2D, AttributeModifierOperation.ADD));
        multimap.put(AttributesLoader.SPELL_SPEED.getName(), new AttributeModifier(SPELL_SPEED_MODIFIER, "Weapon modifier", this.spellSpeed, AttributeModifierOperation.ADD));
        return multimap;
    }
}
