package io.github.dawncraft.core;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;

public class DawnCoreSetuper implements IFMLCallHook
{
    public static Boolean isDeobfEnv;
    public static File mcLocation;
    public static File modLocation;
    
    @Override
    public Void call() throws Exception
    {
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data)
    {
        isDeobfEnv = (Boolean) data.get("runtimeDeobfuscationEnabled");
        mcLocation = (File) data.get("mcLocation");
        modLocation = (File) data.get("coremodLocation");
    }
}
