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
    public static KeyBinding change = new KeyBinding("key." + Dawncraft.MODID + ".switch", Keyboard.KEY_GRAVE, "key.categories." + Dawncraft.MODID);
    public static KeyBinding reload = new KeyBinding("key." + Dawncraft.MODID + ".reload", Keyboard.KEY_R, "key.categories." + Dawncraft.MODID);
    public static KeyBinding use = new KeyBinding("key." + Dawncraft.MODID + ".use", Keyboard.KEY_LMENU, "key.categories." + Dawncraft.MODID);
    public static KeyBinding encyclopedia =  new KeyBinding("key." + Dawncraft.MODID + ".wiki", Keyboard.KEY_H, "key.categories." + Dawncraft.MODID);

    public static void initKeys()
    {
        registerKey(KeyLoader.change);
        registerKey(KeyLoader.reload);
        registerKey(KeyLoader.use);
        registerKey(KeyLoader.encyclopedia);
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
