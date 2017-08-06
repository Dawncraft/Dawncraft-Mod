package io.github.dawncraft.api.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import io.github.dawncraft.config.KeyLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWithInfo extends Item
{
    /** 直接显示还是需要按键才能显示 */
    private boolean show;

    public ItemWithInfo(boolean show)
    {
        this.show = show;
    }
    
    public String getUnlocalizedDesc()
    {
        return this.getUnlocalizedName() + ".desc";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        // Don't use KeyLoader.use.isKeyDown()
        if (this.show || Keyboard.isKeyDown(KeyLoader.use.getKeyCode()))
        {
            tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(this.getUnlocalizedDesc()));
        }
        else
        {
            tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("gui.moreinfo", Keyboard.getKeyName(KeyLoader.use.getKeyCode())));
        }
        
    }
}
