package io.github.dawncraft.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.LogLoader;

/**
 * A Test for Scripts.
 *
 * @author QingChenW
 */
public class ScriptHelper
{
    public ScriptHelper()
    {
        if(ConfigLoader.isColoreggEnabled())
        {
            LogLoader.logger().info("Script Loader Started.");
            
            // create an environment to run in
            Globals globals = JsePlatform.standardGlobals();

            // Use the convenience function on Globals to load a chunk.
            globals.loadfile("/assets/Dawncraft/lua/hello.lua").call();

            // Use any of the "call()" or "invoke()" functions directly on the chunk.
            LuaValue chunk = globals.get(LuaValue.valueOf("print_introduction"));
            chunk.invoke(LuaValue.valueOf("Hello World!"));// new LuaValue[] {}

            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");// JavaScript
            if(engine != null)
            {
                try
                {
                    InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/assets/Dawncraft/lua/hello.js"));
                    engine.eval(reader);
                    reader.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (ScriptException e)
                {
                    e.printStackTrace();
                }
            }
            LogLoader.logger().info("Script Loader Stopped.");
        }
        showEngineList();
    }

    private static void showEngineList()
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factoryList = manager.getEngineFactories();
        System.out.println(factoryList.size());
        for (ScriptEngineFactory factory : factoryList)
        {
            System.out.println(factory.getEngineName() + "=" + factory.getLanguageName());
        }
    }
}
