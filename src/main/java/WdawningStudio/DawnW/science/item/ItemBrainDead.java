package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.potion.PotionLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBrainDead extends ItemFood
{

	public ItemBrainDead()
	{
        super(2, 8.0F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("brainDead");
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
	}
	
    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(PotionLoader.potionBrainDead.id, 160, 1));
            player.addExperience(29);
        }
        super.onFoodEaten(stack, worldIn, player);
    }
}
