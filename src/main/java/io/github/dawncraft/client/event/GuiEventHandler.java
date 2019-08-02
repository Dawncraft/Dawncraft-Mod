package io.github.dawncraft.client.event;

import java.lang.reflect.Field;

import io.github.dawncraft.CommonProxy;
import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.client.event.TextComponentEvent;
import io.github.dawncraft.client.gui.GuiUtils;
import io.github.dawncraft.client.gui.container.GuiInventoryTabs;
import io.github.dawncraft.client.gui.stats.GuiStatsDawn;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Dawncraft.MODID)
public class GuiEventHandler
{
    private static Minecraft mc;
    public static GuiInventoryTabs inventoryTabs;

    static
    {
        mc = Minecraft.getMinecraft();
        inventoryTabs = new GuiInventoryTabs();
    }

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event)
    {
        if (event.getGui() instanceof GuiStats)
        {
            event.setGui(new GuiStatsDawn(GuiEventHandler.mc.currentScreen, GuiEventHandler.mc.player.getStatFileWriter()));
        }
    }

    @SubscribeEvent
    public static void onPostGuiInit(InitGuiEvent.Post event)
    {
        if (event.getGui() instanceof GuiInventory || event.getGui() instanceof GuiContainerCreative)
        {
            InventoryEffectRenderer gui = (InventoryEffectRenderer) event.getGui();
            try
            {
                Field field = ReflectionHelper.findField(GuiContainer.class, "guiLeft", "field_147003_i");
                int guiLeft = field.getInt(gui) - 34 / 2;
                field.setAccessible(true);
                field.setInt(gui, guiLeft);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onPreDrawScreen(DrawScreenEvent.Pre event)
    {

    }

    @SubscribeEvent
    public static void onPostDrawScreen(DrawScreenEvent.Post event)
    {

    }

    @SubscribeEvent
    public static void onPostActionPerformed(ActionPerformedEvent.Post event)
    {
    }

    @SubscribeEvent
    public static void onDrawHoverTextComponent(TextComponentEvent.Hover event)
    {
        if (event.textComponent != null && event.textComponent.getStyle().getHoverEvent() != null)
        {
            HoverEvent hoverEvent = event.textComponent.getStyle().getHoverEvent();

            if (hoverEvent.getAction() == CommonProxy.SHOW_SKILL)
            {
                SkillStack skillStack = null;
                try
                {
                    NBTTagCompound compound = JsonToNBT.getTagFromJson(hoverEvent.getValue().getUnformattedText());
                    skillStack = new SkillStack(compound);
                }
                catch (NBTException e) {}

                if (skillStack != null && skillStack.getSkill() != null)
                {
                    GuiUtils.renderSkillToolTip(event.getGui(), skillStack, event.x, event.y);
                }
                else
                {
                    event.getGui().drawHoveringText(TextFormatting.RED + "Invalid Skill!", event.x, event.y);
                }
            }
        }
    }
}
