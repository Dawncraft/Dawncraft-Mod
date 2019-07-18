package io.github.dawncraft.talent;

import io.github.dawncraft.api.registry.ModData;
import io.github.dawncraft.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * The talent class
 *
 * @author QingChenW
 */
public class Talent extends IForgeRegistryEntry.Impl<Talent>
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

    public void onUpdate() {}
}
