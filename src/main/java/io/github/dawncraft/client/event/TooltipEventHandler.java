package io.github.dawncraft.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 用事件添加一些奇怪的提示
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Dawncraft.MODID)
public class TooltipEventHandler
{
    // 政治家、永垂不朽、烧铝、酵母菌的食用方式、血绿蛋白、大眼激光、略施魔法
    // 无名花-纯白如雪,红艳如血、云南白药-桥边红药
    // 儿童疾走追黄蜂,飞入菜花无处寻
    // orange chicken
    // 金刚经、龙裔之书
    // 喝彩
    protected static Map<Item, IItemTooltipHandler> tooltipMap = new HashMap<>();
    public static IItemTooltipHandler defaultItemHandler = new IItemTooltipHandler()
    {
        @Override
        public List<String> addItemTooltip(ItemStack itemStack, EntityPlayer player, boolean showAdvancedItemTooltips)
        {
            List<String> toolTip = new ArrayList<>();
            toolTip.add(I18n.format(itemStack.getTranslationKey() + ".desc"));
            return toolTip;
        }
    };

    public static void initTooltips()
    {
        IItemTooltipHandler customItemTooltipHandler = new CustomItemTooltipHandler(false);
        registerItemTooltip(ItemInit.SIMPLE_CPU, customItemTooltipHandler);
        registerItemTooltip(ItemInit.ADVANCED_CPU, customItemTooltipHandler);
        registerItemTooltip(ItemInit.PROFESSIONAL_CPU, customItemTooltipHandler);
        if (ConfigLoader.isColoreggEnabled())
        {
            registerItemTooltip(BlockInit.ALARM_CLOCK);
            registerItemTooltip(Items.POTATO);
            registerItemTooltip(ItemInit.HONEY_CHICKEN);
            registerItemTooltip(ItemInit.FROG_STEW);
            registerItemTooltip(ItemInit.MJOLNIR);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        if (tooltipMap.containsKey(event.getItemStack().getItem()))
        {
            int index = event.getFlags().isAdvanced() ? event.getToolTip().size() - 1 : event.getToolTip().size();
            List<String> toolTip = tooltipMap.get(event.getItemStack().getItem()).addItemTooltip(event.getItemStack(), event.getEntityPlayer(), event.getFlags().isAdvanced());
            event.getToolTip().addAll(index, toolTip);
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
            List<String> toolTip = new ArrayList<>();
            // Don't use KeyLoader.use.isKeyDown()
            if (this.show || Keyboard.isKeyDown(KeyLoader.USE.getKeyCode()))
            {
                toolTip.add(TextFormatting.GRAY + I18n.format(I18n.format(itemStack.getTranslationKey() + ".desc")));
            }
            else
            {
                toolTip.add(TextFormatting.GRAY + I18n.format("gui.moreinfo", Keyboard.getKeyName(KeyLoader.USE.getKeyCode())));
            }
            return toolTip;
        }
    }
}
