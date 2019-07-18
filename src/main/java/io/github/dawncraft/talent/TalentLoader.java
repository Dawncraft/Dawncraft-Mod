package io.github.dawncraft.talent;

import io.github.dawncraft.api.registry.ModData;

/**
 * Register some talents.
 *
 * @author QingChenW
 */
public class TalentLoader
{
    public static void initSkills()
    {

    }
    
    private static void register(Talent talent, String name)
    {
        ModData.getTalentRegistry().register(-1, ModData.addPrefix(name), talent);
    }
}
