package io.github.dawncraft.config;

import org.lwjgl.input.Keyboard;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Register keys.
 *
 * @author QingChenW
 */
public class KeyLoader
{
    public static KeyBinding change;
    public static KeyBinding reload;
    public static KeyBinding use;
    public static KeyBinding encyclopedia;

    public static void initKeys()
    {
        String category = "key.categories." + Dawncraft.MODID;
        registerKey(change = new KeyBinding("key." + Dawncraft.MODID + ".switch", Keyboard.KEY_GRAVE, category));
        registerKey(reload = new KeyBinding("key." + Dawncraft.MODID + ".reload", Keyboard.KEY_R, category));
        registerKey(use = new KeyBinding("key." + Dawncraft.MODID + ".use", Keyboard.KEY_LMENU, category));
        registerKey(encyclopedia =  new KeyBinding("key." + Dawncraft.MODID + ".wiki", Keyboard.KEY_H, category));
    }

    /**
     * Register a key binding
     *
     * @param keybinding The key to bind
     */
    private static void registerKey(KeyBinding keybinding)
    {
        ClientRegistry.registerKeyBinding(keybinding);
    }
}
