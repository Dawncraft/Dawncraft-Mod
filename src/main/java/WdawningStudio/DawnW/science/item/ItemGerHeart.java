package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.potion.PotionLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGerHeart extends ItemFood
{
    public ItemGerHeart()
    {
        super(2, 1.0F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("gerHeart");
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
    }
    
    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(PotionLoader.potionGerPower.id, 400, 2));
            player.addExperience(2000);
        }
        super.onFoodEaten(stack, worldIn, player);
    }
}