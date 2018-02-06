package io.github.dawncraft.talent;

/**
 * @author QingChenW
 *
 */
public class Talent
{
    private String unlocalizedName;

    public Talent setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public String getUnlocalizedName()
    {
        return "talent." + this.unlocalizedName;
    }
}
