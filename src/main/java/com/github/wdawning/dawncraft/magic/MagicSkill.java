package com.github.wdawning.dawncraft.magic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class MagicSkill
{
    private Map magicCache = Maps.newHashMap();
    private static final Map SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
	
    public MagicSkill()
    {

    }
    
    public Magic getMagic(int meta)
    {
    	Magic magic = (Magic) this.magicCache.get(Integer.valueOf(meta));

        if (magic == null)
        {
        	magic = Magic.getMagicType(meta);
            this.magicCache.put(Integer.valueOf(meta), magic);
        }

        return magic;
    }
    
    public ItemStack onItemUseFinish(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (!worldIn.isRemote)
        {
            if (!playerIn.capabilities.isCreativeMode)
            {
            	Magic magic = this.getMagic(itemStackIn.getItemDamage());

            	if (magic != null)
            	{
            		magic.spellMagic(magic, itemStackIn, worldIn, playerIn);
            	}
            }
        }

        return itemStackIn;
    }

    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 12;
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = "";

        s = StatCollector.translateToLocal("magic.prefix.spell").trim() + " ";

        String s1;
            
        s1 =  this.getMagic(stack.getItemDamage()).getName();
         
       return s + StatCollector.translateToLocal(s1).trim();
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    } 
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
    {
        int j;

        if (SUB_ITEMS_CACHE.isEmpty())
        {
            for (int i = 0; i <= 1; i++)
            {
                Magic magic = Magic.getMagicType(i);

                if (magic != null)
                {
                	SUB_ITEMS_CACHE.put(magic, Integer.valueOf(i));
                }
             }
         }

         Iterator iterator = SUB_ITEMS_CACHE.values().iterator();

         while (iterator.hasNext())
         {
             j = ((Integer)iterator.next()).intValue();
             subItems.add(new ItemStack(itemIn, 1, j));
         }
    }
}
