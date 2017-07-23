package io.github.dawncraft;

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
import org.markdown4j.Markdown4jProcessor;

import io.github.dawncraft.config.LogLoader;

/**
 * A Test for Scripts.
 *
 * @author QingChenW
 */
public class ScriptTest
{
    public ScriptTest()
    {
        LogLoader.logger().info("Script Loader Started.");
        
        // create an environment to run in
        Globals globals = JsePlatform.standardGlobals();

        // Use the convenience function on Globals to load a chunk.
        globals.loadfile("/assets/dawncraft/lua/hello.lua").call();

        // Use any of the "call()" or "invoke()" functions directly on the chunk.
        LuaValue chunk = globals.get(LuaValue.valueOf("print_introduction"));
        chunk.invoke(LuaValue.valueOf("Hello World!"));// new LuaValue[] {}

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");// JavaScript
        if(engine != null)
        {
            try
            {
                InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/assets/dawncraft/lua/hello.js"));
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

        try
        {
            String html = new Markdown4jProcessor().process("This is a **bold** text");
            LogLoader.logger().info(html);
        } catch (IOException e)
        {
            LogLoader.logger().error("Can't load markdown:", e);
        }

        try
        {
            Metrics metrics = new Metrics(dawncraft.NAME, dawncraft.VERSION);
            metrics.start();
        } catch (IOException e)
        {
            LogLoader.logger().error("Can't load metrics:", e);
        }
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
