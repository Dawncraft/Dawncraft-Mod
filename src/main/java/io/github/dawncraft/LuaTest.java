package io.github.dawncraft;

import java.io.IOException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import io.github.dawncraft.config.LogLoader;

public class LuaTest
{
    public LuaTest()
    {
        LogLoader.logger().info("Lua Loader Started.");

        String script = "assets/dawncraft/lua/hello.lua";

        // create an environment to run in
        Globals globals = JsePlatform.standardGlobals();

        // Use the convenience function on Globals to load a chunk.
        LuaValue chunk = globals.loadfile(script);

        // Use any of the "call()" or "invoke()" functions directly on the chunk.
        chunk.call(LuaValue.valueOf(script));

        LogLoader.logger().info("Lua Loader Stopped.");

        try
        {
            Metrics metrics = new Metrics(dawncraft.NAME, dawncraft.VERSION);
            metrics.start();
        } catch (IOException e)
        {
            LogLoader.logger().error("Can't load metrics:", e);
        }
    }
}
