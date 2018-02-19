package io.github.dawncraft.core;

import java.util.Arrays;
import com.google.common.eventbus.EventBus;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class DawnCoreModContainer extends DummyModContainer
{
    public DawnCoreModContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = this.getMetadata();
        meta.modId = "dawncore";
        meta.name = "DawnCore";
        meta.version = "0.0.1";
        meta.authorList = Arrays.asList("QingChenW");
        meta.credits = "Thanks to szszss's core mod tutorial and other people.";
        meta.description = "A core mod for dawncraft mod. It will change a lot in the future.";
        meta.url = "https://github.com/Dawncraft/Dawncraft-Mod";
    }
    
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        return true;
    }
}
