package io.github.dawncraft;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import io.github.dawncraft.config.ConfigLoader;

/**
 * All categories of Dawncraft mod.
 *
 * @author QingChenW
 */
public enum ModCategories
{
    DEFAULT(CATEGORY_GENERAL)
    {
        @Override
        public boolean isEnabled()
        {
            return true;
        }
    },
    ENERGY("energy")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isEnergyEnabled;
        }
    },
    MAGNET("magnet")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isMagnetEnabled;
        }
    },
    MACHINE("machine")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isMachineEnabled;
        }
    },
    COMPUTER("computer")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isComputerEnabled;
        }
    },
    SCIENCE("science")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isScienceEnabled;
        }
    },
    FURNITURE("furniture")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isFurnitureEnabled;
        }
    },
    CUISINE("cuisine")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isCuisineEnabled;
        }
    },
    WAR("war")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isWarEnabled;
        }
    },
    MAGIC("magic")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isMagicEnabled;
        }
    },
    COLOREGG("coloregg")
    {
        @Override
        public boolean isEnabled()
        {
            return ConfigLoader.isColoreggEnabled();
        }
    };
    
    private String name;

    private ModCategories(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }

    public abstract boolean isEnabled();
}