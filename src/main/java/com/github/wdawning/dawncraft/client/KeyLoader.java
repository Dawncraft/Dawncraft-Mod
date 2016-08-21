package com.github.wdawning.dawncraft.client;

import org.lwjgl.input.Keyboard;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyLoader
{
    public static KeyBinding aim;
    public static KeyBinding magic;
    
    public KeyLoader()
    {
        KeyLoader.aim = new KeyBinding("key." + dawncraft.MODID + ".aim", Keyboard.KEY_F, "key.categories." + dawncraft.MODID);
        KeyLoader.magic = new KeyBinding("key." + dawncraft.MODID + ".magic", Keyboard.KEY_R, "key.categories." + dawncraft.MODID);

        ClientRegistry.registerKeyBinding(KeyLoader.aim);
        ClientRegistry.registerKeyBinding(KeyLoader.magic);
    }
}
