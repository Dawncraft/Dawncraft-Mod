package io.github.dawncraft.api.registry;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.talent.Talent;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * Register mod's registry.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class ModData
{
    public static final ResourceLocation SKILLS = new ResourceLocation(Dawncraft.MODID + ":" + "skills");
    public static final ResourceLocation TALENTS = new ResourceLocation(Dawncraft.MODID + ":" + "talents");
    
    private static final int MAX_SKILL_ID = 31999;
    private static final int MAX_TALENT_ID = 4095;
    
    @SubscribeEvent
    public static void newRegistry(RegistryEvent.NewRegistry event)
    {
    	new RegistryBuilder<Skill>().setName(SKILLS).setType(Skill.class).setMaxID(MAX_SKILL_ID).create();
    	new RegistryBuilder<Talent>().setName(TALENTS).setType(Talent.class).setMaxID(MAX_TALENT_ID).create();
    }
}
