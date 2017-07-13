package io.github.dawncraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Multimap;

import io.github.dawncraft.creativetab.CreativeTabsLoader;

public class ItemHammerMjolnir extends Item
{
    private float attackDamage;
    private final Item.ToolMaterial material;
    
    public ItemHammerMjolnir()
    {
    	this.material = ItemLoader.HAMMER;
    	this.setUnlocalizedName("mjolnir");
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
        this.attackDamage = 6.0F + material.getDamageVsEntity();
    }
    
    public float getDamageVsEntity()
    {
        return this.material.getDamageVsEntity();
    }
    
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }
    
    public String getToolMaterialName()
    {
        return this.material.toString();
    }
    
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
    
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn)
    {
        if ((double)blockIn.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, playerIn);
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double)this.attackDamage, 0));
        return multimap;
    }
}
