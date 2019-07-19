package io.github.dawncraft.client.event;

import java.lang.reflect.Field;

import io.github.dawncraft.api.client.event.TextComponentEvent;
import io.github.dawncraft.client.gui.GuiUtils;
import io.github.dawncraft.client.gui.container.GuiInventoryTabs;
import io.github.dawncraft.client.gui.stats.GuiStatsDawn;
import io.github.dawncraft.event.EventLoader;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GuiEventHandler
{
    private Minecraft mc;
    public GuiInventoryTabs inventoryTabs;

    public GuiEventHandler()
    {
	this.mc = Minecraft.getMinecraft();
	this.inventoryTabs = new GuiInventoryTabs();
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
	if (event.getGui() instanceof GuiStats)
	{
	    event.setGui(new GuiStatsDawn(this.mc.currentScreen, this.mc.player.getStatFileWriter()));
	}
    }

    @SubscribeEvent
    public void onPostGuiInit(InitGuiEvent.Post event)
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
    public void onPreDrawScreen(DrawScreenEvent.Pre event)
    {

    }

    @SubscribeEvent
    public void onPostDrawScreen(DrawScreenEvent.Post event)
    {

    }

    @SubscribeEvent
    public void onPostActionPerformed(ActionPerformedEvent.Post event)
    {
    }

    @SubscribeEvent
    public void onDrawHoverTextComponent(TextComponentEvent.Hover event)
    {
	if (event.textComponent != null && event.textComponent.getStyle().getHoverEvent() != null)
	{
	    HoverEvent hoverEvent = event.textComponent.getStyle().getHoverEvent();

	    if (hoverEvent.getAction() == EventLoader.SHOW_SKILL)
	    {
		SkillStack skillStack = null;
		try
		{
		    NBTBase nbt = JsonToNBT.getTagFromJson(hoverEvent.getValue().getUnformattedText());

		    if (nbt instanceof NBTTagCompound)
		    {
			skillStack = SkillStack.loadSkillStackFromNBT((NBTTagCompound) nbt);
		    }
		}
		catch (NBTException e) {}

		if (skillStack != null)
		{
		    GuiUtils.renderSkillToolTip(event.getGui(), skillStack, event.x, event.y);
		}
		else
		{
		    event.getGui().drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Skill!", event.x, event.y);
		}
	    }
	}
    }
}
