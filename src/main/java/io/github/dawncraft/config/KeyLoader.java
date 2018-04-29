package io.github.dawncraft.config;

import org.lwjgl.input.Keyboard;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register keys.
 *
 * @author QingChenW
 */
public class KeyLoader
{
    public static KeyBinding change = new KeyBinding("key." + Dawncraft.MODID + ".switch", Keyboard.KEY_GRAVE, "key.categories." + Dawncraft.MODID);
    public static KeyBinding magic = new KeyBinding("key." + Dawncraft.MODID + ".magic", Keyboard.KEY_R, "key.categories." + Dawncraft.MODID);
    public static KeyBinding use = new KeyBinding("key." + Dawncraft.MODID + ".use", Keyboard.KEY_LMENU, "key.categories." + Dawncraft.MODID);
    public static KeyBinding Encyclopedia =  new KeyBinding("key." + Dawncraft.MODID + ".wiki", Keyboard.KEY_H, "key.categories." + Dawncraft.MODID);
    
    public KeyLoader(FMLInitializationEvent event)
    {
        register(KeyLoader.change);
        register(KeyLoader.magic);
        register(KeyLoader.use);
        register(KeyLoader.Encyclopedia);
    }

    /**
     * Register a key binding
     * 
     * @param keybinding The key to bind
     */
    private static void register(KeyBinding keybinding)
    {
        ClientRegistry.registerKeyBinding(keybinding);
    }
}
