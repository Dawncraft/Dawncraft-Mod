package io.github.dawncraft.client.event;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityInit;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.gui.GuiEncyclopedia;
import io.github.dawncraft.client.gui.container.GuiSkillInventory;
import io.github.dawncraft.client.sound.SoundInit;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.network.MessageOpenSkillInventory;
import io.github.dawncraft.network.MessageSpellSkillChange;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handle some input events.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Dawncraft.MODID)
public class InputEventHandler
{
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) mc.getRenderViewEntity();
            // Switch key was pressed
            if (KeyLoader.CHANGE.isPressed())
            {
                ClientProxy.getInstance().getIngameGUIDawn().switchMode();
            }
            // Spell key was pressed
            if (!mc.playerController.isSpectator() && ClientProxy.getInstance().getIngameGUIDawn().spellMode)
            {
                for (int i = 0; i < mc.gameSettings.keyBindsHotbar.length; i++)
                {
                    if (mc.gameSettings.keyBindsHotbar[i].isPressed())
                    {
                        IPlayerMagic playerMagic = player.getCapability(CapabilityInit.PLAYER_MAGIC, null);
                        SkillStack skillStack = playerMagic.getSkillInventory().getSkillStackInSlot(i);
                        if (skillStack != null && (playerMagic.getSpellAction() == EnumSpellAction.NONE || playerMagic.getSkillInventory().getSkillStackInSlot(i) != playerMagic.getSkillInSpell()))
                        {
                            ClientProxy.getInstance().getIngameGUIDawn().setSpellIndex(i);
                            NetworkLoader.instance.sendToServer(new MessageSpellSkillChange(i));
                            mc.getSoundHandler().playSound(SoundInit.createSound(SoundEvents.UI_BUTTON_CLICK));
                        }
                    }
                }
            }
            // Reload key was pressed
            if (KeyLoader.RELOAD.isPressed())
            {
                NetworkLoader.instance.sendToServer(new MessageOpenSkillInventory());
                mc.displayGuiScreen(new GuiSkillInventory(player));
            }
            // Use key was pressed
            if (KeyLoader.USE.isPressed())
            {

            }
        }
        // Wiki key was pressed
        if (KeyLoader.ENCYCLOPEDIA.isPressed())
        {
            mc.displayGuiScreen(new GuiEncyclopedia());
        }
    }
}
