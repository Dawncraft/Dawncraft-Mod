package io.github.dawncraft.client.event;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.client.gui.magic.GuiMagic;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.util.WebBrowserV1;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Register some client events.
 *
 * @author QingChenW
 */
public class ClientEventLoader
{
    public ClientEventLoader(FMLInitializationEvent event)
    {
        registerEvent(new InputClientEventHandler(event));
        registerEvent(new GuiInGameClientEventHandler(event));
    }

    private static void registerEvent(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
