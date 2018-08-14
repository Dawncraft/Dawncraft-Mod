package io.github.dawncraft.talent;

import io.github.dawncraft.api.ModData;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

/**
 * 天赋基类
 *
 * @author QingChenW
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
    
    public void onUpdate() {}

    /* ======================================== REGISTER START =====================================*/
    public static final RegistryNamespaced<ResourceLocation, Talent> talentRegistry = ModData.getTalentRegistry();
    public final RegistryDelegate<Talent> delegate = ((FMLControlledNamespacedRegistry)talentRegistry).getDelegate(this, Talent.class);
    
    public static int getIdFromSkill(Talent talent)
    {
        return talent == null ? 0 : talentRegistry.getIDForObject(talent);
    }

    public static Talent getTalentById(int id)
    {
        return talentRegistry.getObjectById(id);
    }

    public static Talent getByNameOrId(String id)
    {
        Talent talent = talentRegistry.getObject(new ResourceLocation(id));

        if (talent == null)
        {
            try
            {
                return getTalentById(Integer.parseInt(id));
            }
            catch (NumberFormatException e) {}
        }

        return talent;
    }
    
    private ResourceLocation registryName = null;
    
    public final String getRegistryName()
    {
        if (this.delegate.getResourceName() != null) return this.delegate.getResourceName().toString();
        return this.registryName != null ? this.registryName.toString() : null;
    }
    
    public final Talent setRegistryName(String name)
    {
        if (this.getRegistryName() != null)
            throw new IllegalStateException("Attempted to set registry name on skill with exisiting registry name! New: " + name + " Old: " + this.getRegistryName());
        int index = name.lastIndexOf(':');
        String oldPrefix = index == -1 ? "" : name.substring(0, index);
        name = index == -1 ? name : name.substring(index + 1);
        net.minecraftforge.fml.common.ModContainer mc = net.minecraftforge.fml.common.Loader.instance().activeModContainer();
        String prefix = mc == null ? "minecraft" : mc.getModId();
        if (!oldPrefix.equals(prefix) && oldPrefix.length() > 0)
        {
            net.minecraftforge.fml.common.FMLLog.bigWarning("Dangerous alternative prefix %s for name %s, invalid registry invocation/invalid name?", name.substring(0, index), name);
            prefix = oldPrefix;
        }
        this.registryName = new ResourceLocation(prefix, name);
        return this;
    }
    public final Talent setRegistryName(ResourceLocation name){ return this.setRegistryName(name.toString()); }
    public final Talent setRegistryName(String modID, String name){ return this.setRegistryName(modID + ":" + name); }
    /* ======================================== REGISTER END   =====================================*/
}
