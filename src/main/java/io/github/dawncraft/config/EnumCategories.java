package io.github.dawncraft.config;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

/**
 * All categories of Dawncraft mod.
 *
 * @author QingChenW
 */
public enum EnumCategories
{
    DEFAULT(CATEGORY_GENERAL),
    ENERGY("energy"),
    MAGNET("magnet"),
    MACHINE("machine"),
    COMPUTER("computer"),
    SCIENCE("science"),
    FURNITURE("furniture"),
    CUISINE("cuisine"),
    WEAPON("weapon"),
    MAGIC("magic"),
    COLOREGG("coloregg");

    private String name;
    
    private EnumCategories(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
