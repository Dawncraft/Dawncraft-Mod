package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.entity.EntityMagnetBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagnetBall extends Item
{
    public ItemMagnetBall()
    {
        super();
        this.setUnlocalizedName("magnetBall");
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityMagnetBall(worldIn, playerIn));
        }
        return itemStackIn;
    }
}
