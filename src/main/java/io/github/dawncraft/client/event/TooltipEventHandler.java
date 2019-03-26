package io.github.dawncraft.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Keyboard;

/**
 * 用事件添加一些奇怪的提示
 *
 * @author QingChenW
 */
public class TooltipEventHandler
{
    // 政治家、永垂不朽、烧铝、酵母菌的食用方式、血绿蛋白、大眼激光、略施魔法
    private static Map<Item, IItemTooltipHandler> tooltipMap = new HashMap<Item, IItemTooltipHandler>();
    // EnumChatFormatting.GRAY
    public static IItemTooltipHandler defaultItemHandler = new IItemTooltipHandler()
    {
        @Override
        public List<String> addItemTooltip(ItemStack itemStack, EntityPlayer player, boolean showAdvancedItemTooltips)
        {
            List<String> toolTip = new ArrayList<String>();
            toolTip.add(I18n.format(itemStack.getUnlocalizedName() + ".desc"));
            return toolTip;
        }
    };

    public TooltipEventHandler()
    {
        IItemTooltipHandler customItemTooltipHandler = new CustomItemTooltipHandler(false);
        registerItemTooltip(ItemLoader.simpleCPU, customItemTooltipHandler);
        registerItemTooltip(ItemLoader.advancedCPU, customItemTooltipHandler);
        registerItemTooltip(ItemLoader.superCPU, customItemTooltipHandler);
        if(ConfigLoader.isColoreggEnabled())
        {
            registerItemTooltip(BlockLoader.alarmClock);
            registerItemTooltip(Items.potato);
            registerItemTooltip(ItemLoader.honeyChicken);
            registerItemTooltip(ItemLoader.frogStew);
            registerItemTooltip(ItemLoader.mjolnir);
        }
    }
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event)
    {
        if(tooltipMap.containsKey(event.itemStack.getItem()))
        {
            int index = event.showAdvancedItemTooltips ? event.toolTip.size() - 1 : event.toolTip.size();
            List<String> toolTip = tooltipMap.get(event.itemStack.getItem()).addItemTooltip(event.itemStack, event.entityPlayer, event.showAdvancedItemTooltips);
            event.toolTip.addAll(index, toolTip);
        }
    }

    private static void registerItemTooltip(Item item)
    {
        registerItemTooltip(item, defaultItemHandler);
    }

    private static void registerItemTooltip(Block block)
    {
        registerItemTooltip(block, defaultItemHandler);
    }
    
    public static void registerItemTooltip(Item item, IItemTooltipHandler handler)
    {
        tooltipMap.put(item, handler == null ? defaultItemHandler : handler);
    }
    
    public static void registerItemTooltip(Block block, IItemTooltipHandler handler)
    {
        registerItemTooltip(Item.getItemFromBlock(block), handler);
    }
    
    public interface IItemTooltipHandler
    {
        List<String> addItemTooltip(ItemStack itemStack, EntityPlayer player, boolean showAdvancedItemTooltips);
    }

    public static class CustomItemTooltipHandler implements IItemTooltipHandler
    {
        /** If need press key to show information */
        private boolean show;
        
        public CustomItemTooltipHandler(boolean show)
        {
            this.show = show;
        }

        @Override
        public List<String> addItemTooltip(ItemStack itemStack, EntityPlayer player, boolean showAdvancedItemTooltips)
        {
            List<String> toolTip = new ArrayList<String>();
            // Don't use KeyLoader.use.isKeyDown()
            if (this.show || Keyboard.isKeyDown(KeyLoader.use.getKeyCode()))
            {
                toolTip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(I18n.format(itemStack.getUnlocalizedName() + ".desc")));
            }
            else
            {
                toolTip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("gui.moreinfo", Keyboard.getKeyName(KeyLoader.use.getKeyCode())));
            }
            return toolTip;
        }
    }
}
