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
    public static final KeyBinding CHANGE;
    public static final KeyBinding RELOAD;
    public static final KeyBinding USE;
    public static final KeyBinding ENCYCLOPEDIA;

    static
    {
        String category = "key.categories." + Dawncraft.MODID;
        CHANGE = new KeyBinding("key." + Dawncraft.MODID + ".switch", Keyboard.KEY_GRAVE, category);
        RELOAD = new KeyBinding("key." + Dawncraft.MODID + ".reload", Keyboard.KEY_R, category);
        USE = new KeyBinding("key." + Dawncraft.MODID + ".use", Keyboard.KEY_LMENU, category);
        ENCYCLOPEDIA =  new KeyBinding("key." + Dawncraft.MODID + ".wiki", Keyboard.KEY_H, category);
    }

    public static void initKeys()
    {
        registerKey(CHANGE);
        registerKey(RELOAD);
        registerKey(USE);
        registerKey(ENCYCLOPEDIA);
    }

    /**
     * Register a key binding
     *
     * @param keyBinding The key to bind
     */
    private static void registerKey(KeyBinding keyBinding)
    {
        ClientRegistry.registerKeyBinding(keyBinding);
    }
}
