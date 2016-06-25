package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFaeces extends ItemFood
{
    public ItemFaeces()
    {
        super(1, 0.0F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("faeces");
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
    }
    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 1));
            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 1));
            player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
            player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 1));
        }
        super.onFoodEaten(stack, worldIn, player);
    }
}