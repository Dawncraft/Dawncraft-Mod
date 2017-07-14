package io.github.dawncraft.magic;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMana;
import io.github.dawncraft.network.MessageMana;
import io.github.dawncraft.network.NetworkLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author QingChenW
 *
 */
public class Skill
{
    public final int id;
    public final int mana;
    public static String name = "";
    
    public Skill(int magicID, int magicMANA)
    {
        this.id = magicID;
        this.mana = magicMANA;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public int getMana()
    {
        return this.mana;
    }

    public Skill setMagicName(String nameIn)
    {
        this.name = nameIn;
        return this;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public boolean spellMagic(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if(!worldIn.isRemote && playerIn.hasCapability(CapabilityLoader.mana, null))
        {
              IMana manaCap = playerIn.getCapability(CapabilityLoader.mana, null);
              int mp = manaCap.getMana();
              if(mp >= 4)
              {
	                mp = mp - 4;
	                playerIn.heal(12.0F);
	                manaCap.setMana(mp);
	                
	                MessageMana message = new MessageMana();
	                message.nbt.setInteger("mana", mp);
	                NetworkLoader.instance.sendTo(message, (EntityPlayerMP) playerIn);
	                
	                for(int i = 0; i < 4; i++)
	                {
	                	Random rand = new Random();
	                	double d0 = (double)((float)playerIn.getPosition().getX() + rand.nextFloat());
	                	double d1 = (double)((float)playerIn.getPosition().getY() + 0.8F);
	                	double d2 = (double)((float)playerIn.getPosition().getZ() + rand.nextFloat());
	                	double d3 = 0.0D;
	                	double d4 = 0.0D;
	                	double d5 = 0.0D;
	                	worldIn.spawnParticle(EnumParticleTypes.SPELL_INSTANT, d0, d1, d2, d3, d4, d5, new int[0]);
	                }
	                  
	                onSpellMagicFinish(itemStackIn, worldIn, playerIn);
	                return true;
              }
        }
		return false;
    }
    
    public void onSpellMagicFinish(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {

    }
}
