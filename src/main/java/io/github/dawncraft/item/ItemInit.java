package io.github.dawncraft.item;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ItemAxe;
import io.github.dawncraft.api.item.ItemGlove;
import io.github.dawncraft.api.item.ItemGunLauncher;
import io.github.dawncraft.api.item.ItemGunRifle;
import io.github.dawncraft.api.item.ItemHammer;
import io.github.dawncraft.api.item.ItemSkull;
import io.github.dawncraft.api.item.ItemWand;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.client.sound.SoundInit;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.potion.PotionInit;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.GameData;

/**
 * Register some items.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class ItemInit
{
    // Action
    public static final EnumAction SHOOT = EnumHelper.addAction("SHOOT");
    public static final EnumAction RELOAD = EnumHelper.addAction("RELOAD");

    public static final Item.ToolMaterial MAGNET_TOOL = EnumHelper.addToolMaterial("MAGNET", 2, 285, 6.0F, 2.0F, 11);
    public static final ItemArmor.ArmorMaterial MAGNET_ARMOR = EnumHelper.addArmorMaterial("MAGNET", Dawncraft.MODID + ":" + "magnet", 17, new int[] { 1, 5, 4, 2 }, 11, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final ItemArmor.ArmorMaterial COPPER_ARMOR = EnumHelper.addArmorMaterial("COPPER", Dawncraft.MODID + ":" + "copper", 13, new int[] { 1, 4, 3, 1 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    // Redstone
    public static Item MAGNET_DOOR;
    public static Item MAGNET_CARD;

    // Materials/Misc
    public static Item MAGNET;
    public static Item MAGNET_INGOT;
    public static Item MAGNET_STICK;
    public static Item MAGNET_BALL;

    public static Item COPPER_INGOT;

    // Tools
    public static Item WOOD_HAMMER;
    public static Item STONE_HAMMER;
    public static Item IRON_HAMMER;
    public static Item GOLD_HAMMER;
    public static Item DIAMOND_HAMMER;
    public static Item MAGNET_AXE;
    public static Item MAGNET_PICKAXE;
    public static Item MAGNET_SHOVEL;
    public static Item MAGNET_HAMMER;
    public static Item MAGNET_HOE;

    // Compat
    public static Item WOOD_WAND;
    public static Item STONE_WAND;
    public static Item IRON_WAND;
    public static Item GOLD_WAND;
    public static Item DIAMOND_WAND;
    public static Item MAGNET_SWORD;
    public static Item MAGNET_WAND;
    public static Item MAGNET_HELMET;
    public static Item MAGNET_CHESTPLATE;
    public static Item MAGNET_LEGGINGS;
    public static Item MAGNET_BOOTS;

    // Science

    // Energy

    // Machine

    // Computer
    public static Item SIMPLE_CPU;
    public static Item ADVANCED_CPU;
    public static Item PROFESSIONAL_CPU;

    // Furniture

    // Cuisine
    public static Item TUMBLER;
    public static Item FROG;
    public static Item HONEY;
    public static Item COOKED_EGG;
    public static Item HONEY_CHICKEN;
    public static Item HONEY_STEW;
    public static Item FROG_STEW;

    // Weapons
    public static Item GUN_AK47;
    public static Item GUN_RPG;
    public static Item GUN_BULLET;
    public static Item GUN_ROCKET;
    public static Item THROWABLE_TORCH;

    // Magic
    public static Item MAGIC_DUST;
    public static Item SKILL_BOOK;

    // ColourEgg
    public static final Item.ToolMaterial GOLDIAMOND_TOOL = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F, 16);
    public static final Item.ToolMaterial MJOLNIR_WEAPON = EnumHelper.addToolMaterial("MJOLNIR", 4, 2586, 10.0F, 2.0F, 24);

    public static ItemSkull SKULL;
    public static Item FAECES;
    public static Item GER_HEART;
    public static Item BRAIN_DEAD;
    public static Item FUNNY;

    public static Item RECORD_DJ;
    public static Item RECORD_WZSONGS;

    public static Item GOLDIAMOND_SWORD;
    public static Item MJOLNIR;
    public static Item INFINITY_GAUNTLET;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        // Building blocks
        registerItemBlock(BlockInit.COPPER_ORE);
        registerItemBlock(BlockInit.COPPER_BLOCK);

        registerItemBlock(BlockInit.MAGNET_ORE);
        registerItemBlock(BlockInit.MAGNET_BLOCK);

        // Decorations
        registerItemBlock(BlockInit.MAGNET_CHEST);

        // Redstone
        MAGNET_DOOR = registerItemBlock(BlockInit.MAGNET_DOOR, new ItemMagnetDoor().setTranslationKey("magnetDoor").setCreativeTab(CreativeTabs.REDSTONE), "magnet_door");

        MAGNET_CARD = registerItem(new ItemMagnetCard().setTranslationKey("magnetCard").setCreativeTab(CreativeTabs.REDSTONE), "magnet_card");

        // Transportation
        registerItemBlock(BlockInit.MAGNET_RAIL);

        // Materials/Misc
        COPPER_INGOT = registerItem(new Item().setTranslationKey("copperIngot").setCreativeTab(CreativeTabs.MATERIALS), "copper_ingot");

        MAGNET = registerItem(new Item().setTranslationKey("magnet").setCreativeTab(CreativeTabs.MATERIALS), "magnet");
        MAGNET_INGOT = registerItem(new Item().setTranslationKey("magnetIngot").setCreativeTab(CreativeTabs.MATERIALS), "magnet_ingot");
        MAGNET_TOOL.setRepairItem(new ItemStack(MAGNET_INGOT));
        MAGNET_STICK = registerItem(new Item().setTranslationKey("magnetStick").setCreativeTab(CreativeTabs.MATERIALS), "magnet_stick");
        MAGNET_BALL = registerItem(new ItemMagnetBall().setTranslationKey("magnetBall").setCreativeTab(CreativeTabs.MISC), "magnet_ball");

        // Tools
        WOOD_HAMMER = registerItem(new ItemHammer(ToolMaterial.WOOD).setTranslationKey("woodHammer"), "wood_hammer");
        STONE_HAMMER = registerItem(new ItemHammer(ToolMaterial.STONE).setTranslationKey("stoneHammer"), "stone_hammer");
        IRON_HAMMER = registerItem(new ItemHammer(ToolMaterial.IRON).setTranslationKey("ironHammer"), "iron_hammer");
        GOLD_HAMMER = registerItem(new ItemHammer(ToolMaterial.GOLD).setTranslationKey("goldHammer"), "gold_hammer");
        DIAMOND_HAMMER = registerItem(new ItemHammer(ToolMaterial.DIAMOND).setTranslationKey("diamondHammer"), "diamond_hammer");
        MAGNET_AXE = registerItem(new ItemAxe(MAGNET_TOOL, 8.0F,-3.2F).setTranslationKey("magnetAxe"), "magnet_axe");
        MAGNET_PICKAXE = registerItem(new ItemPickaxe(MAGNET_TOOL).setTranslationKey("magnetPickaxe"), "magnet_pickaxe");
        MAGNET_SHOVEL = registerItem(new ItemSpade(MAGNET_TOOL).setTranslationKey("magnetSpade"), "magnet_shovel");
        MAGNET_HAMMER = registerItem(new ItemHammer(MAGNET_TOOL).setTranslationKey("magnetHammer"), "magnet_hammer");
        MAGNET_HOE = registerItem(new ItemHoe(MAGNET_TOOL).setTranslationKey("magnetHoe"), "magnet_hoe");

        // Combat
        WOOD_WAND = registerItem(new ItemWand(Item.ToolMaterial.WOOD, 0.05F).setTranslationKey("woodWand"), "wood_wand");
        STONE_WAND = registerItem(new ItemWand(Item.ToolMaterial.STONE, 0.10F).setTranslationKey("stoneWand"), "stone_wand");
        IRON_WAND = registerItem(new ItemWand(Item.ToolMaterial.IRON, 0.20F).setTranslationKey("ironWand"), "iron_wand");
        GOLD_WAND = registerItem(new ItemWand(Item.ToolMaterial.GOLD, 0.35F).setTranslationKey("goldWand"), "gold_wand");
        DIAMOND_WAND = registerItem(new ItemWand(Item.ToolMaterial.DIAMOND, 0.30F).setTranslationKey("diamondWand"), "diamond_wand");
        MAGNET_SWORD = registerItem(new ItemSword(MAGNET_TOOL).setTranslationKey("magnetSword"), "magnet_sword");
        MAGNET_WAND = registerItem(new ItemWand(MAGNET_TOOL, 0.20F).setTranslationKey("magnetWand"), "magnet_wand");
        MAGNET_HELMET = registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.HEAD).setTranslationKey("magnetHelmet"), "magnet_helmet");
        MAGNET_CHESTPLATE = registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.CHEST).setTranslationKey("magnetChestplate"), "magnet_chestplate");
        MAGNET_LEGGINGS = registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.LEGS).setTranslationKey("magnetLeggings"), "magnet_leggings");
        MAGNET_BOOTS = registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.FEET).setTranslationKey("magnetBoots"), "magnet_boots");

        // Science

        // Energy
        registerItemBlock(BlockInit.ELECTRIC_CABLE);
        registerItemBlock(BlockInit.HEAT_GENERATOR);
        registerItemBlock(BlockInit.FLUID_GENERATOR);
        registerItemBlock(BlockInit.WIND_GENERATOR);
        registerItemBlock(BlockInit.SOLAR_GENERATOR);
        registerItemBlock(BlockInit.NUCLEAR_GENERATOR);
        registerItemBlock(BlockInit.MAGIC_GENERATOR);

        // Machine
        registerItemBlock(BlockInit.MACHINE_FURNACE);

        // Computer
        registerItemBlock(BlockInit.SIMPLE_COMPUTER);
        registerItemBlock(BlockInit.ADVANCED_COMPUTER);
        registerItemBlock(BlockInit.PROFESSIONAL_COMPUTER);

        SIMPLE_CPU = registerItem(new Item().setTranslationKey("simpleCPU").setCreativeTab(CreativeTabsLoader.COMPUTER), "simple_cpu");
        ADVANCED_CPU = registerItem(new Item().setTranslationKey("advancedCPU").setCreativeTab(CreativeTabsLoader.COMPUTER), "advanced_cpu");
        PROFESSIONAL_CPU = registerItem(new Item().setTranslationKey("professionalCPU").setCreativeTab(CreativeTabsLoader.COMPUTER), "professional_cpu");

        // Furniture
        registerItemBlock(BlockInit.WOOD_TABLE);
        registerItemBlock(BlockInit.STONE_TABLE);
        registerItemBlock(BlockInit.WOOD_CHAIR);
        registerItemBlock(BlockInit.STONE_CHAIR);
        registerItemBlock(BlockInit.ALARM_CLOCK);

        // Cuisine
        TUMBLER = registerItem(new Item().setTranslationKey("tumbler").setCreativeTab(CreativeTabsLoader.CUISINE), "tumbler");

        FROG = registerItem(new Item().setTranslationKey("frog").setCreativeTab(CreativeTabsLoader.CUISINE), "frog");
        HONEY = registerItem(new Item().setTranslationKey("honey").setCreativeTab(CreativeTabsLoader.CUISINE), "honey");
        COOKED_EGG = registerItem(new ItemFood(2, 0.2F, false).setTranslationKey("eggCooked").setCreativeTab(CreativeTabsLoader.CUISINE), "cooked_egg");
        HONEY_CHICKEN = registerItem(new ItemFood(8, 0.6F, true).setTranslationKey("chickenHoney").setCreativeTab(CreativeTabsLoader.CUISINE), "honey_chicken");
        HONEY_STEW = registerItem(new ItemSoup(2).setTranslationKey("honeyStew").setCreativeTab(CreativeTabsLoader.CUISINE), "honey_stew");
        FROG_STEW = registerItem(new ItemSoup(4).setTranslationKey("frogStew").setCreativeTab(CreativeTabsLoader.CUISINE), "frog_stew");

        // Weapons
        GUN_AK47 = registerItem(new ItemGunRifle(423, 30, 69, 2, 1, 0.85F, 0.70F, 0.65F, 6.0F).setTranslationKey("gunAK47").setCreativeTab(CreativeTabsLoader.WEAPONS), "gun_ak47");
        GUN_BULLET = registerItem(new Item().setTranslationKey("gunBullet").setCreativeTab(CreativeTabsLoader.WEAPONS), "gun_bullet");
        GUN_RPG = registerItem(new ItemGunLauncher(28, 3, 100, -1, 1, 0.5F, 0.5F, 0.95F).setTranslationKey("gunRPG").setCreativeTab(CreativeTabsLoader.WEAPONS), "gun_rpg");
        GUN_ROCKET = registerItem(new Item().setTranslationKey("gunRocket").setCreativeTab(CreativeTabsLoader.WEAPONS).setMaxStackSize(16), "gun_rocket");
        THROWABLE_TORCH = registerItem(new ItemThrowableTorch().setTranslationKey("throwableTorch").setCreativeTab(CreativeTabsLoader.WEAPONS), "throwable_torch");

        // Magic
        registerItemBlock(BlockInit.MAGIC_ORE);

        MAGIC_DUST = registerItem(new Item().setTranslationKey("magicDust").setCreativeTab(CreativeTabsLoader.MAGIC), "magic_dust");
        SKILL_BOOK = registerItem(new ItemSkillBook().setTranslationKey("skillBook").setCreativeTab(CreativeTabsLoader.MAGIC), "skill_book");

        // ColourEgg
        registerItemBlock(BlockInit.SUPER_CHEST);
        SKULL = (ItemSkull) registerItemBlock(BlockInit.SKULL, new ItemSkull(BlockInit.SKULL, EnumSkullType.VALUES).setTranslationKey("skull").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "skull");

        FAECES = registerItem(new ItemFood(1, 0.0F, true)
        {
            @Override
            public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
            {
                super.onFoodEaten(stack, world, player);
                if (!world.isRemote)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 1));
                    player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
                    player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 200, 1));
                    player.addPotionEffect(new PotionEffect(PotionInit.CONFUSION, 200, 1));
                }
            }
        }.setAlwaysEdible().setTranslationKey("faeces").setCreativeTab(CreativeTabsLoader.CUISINE), "faeces");
        GER_HEART = registerItem(new ItemFood(2, 1.0F, false)
        {
            @Override
            public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
            {
                super.onFoodEaten(stack, world, player);
                if (!world.isRemote)
                {
                    player.addPotionEffect(new PotionEffect(PotionInit.GER_POWER, 400, 2));
                    player.addExperience(2000);
                }
            }
        }.setAlwaysEdible().setTranslationKey("gerHeart").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "ger_heart");
        BRAIN_DEAD = registerItem(new ItemFood(2, 8.0F, false)
        {
            @Override
            public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
            {
                super.onFoodEaten(stack, world, player);
                if (!world.isRemote)
                {
                    player.addPotionEffect(new PotionEffect(PotionInit.BRAIN_DEAD, 160, 1));
                    player.addExperience(233);
                }
            }
        }.setAlwaysEdible().setTranslationKey("brainDead").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "brain_dead");
        FUNNY = registerItem(new Item().setTranslationKey("funny").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "funny");

        RECORD_DJ = registerItem(new ItemRecord("dj", SoundInit.RECORDS_DJ).setTranslationKey("record").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "record_dj");
        RECORD_WZSONGS = registerItem(new ItemRecord("wzsongs", SoundInit.RECORDS_WZSONGS).setTranslationKey("record").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "record_wzsongs");

        GOLDIAMOND_SWORD = registerItem(new ItemSword(ItemInit.GOLDIAMOND_TOOL).setTranslationKey("goldiamondSword").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "goldiamond_sword");
        MJOLNIR = registerItem(new ItemHammer(ItemInit.MJOLNIR_WEAPON)
        {
            @Override
            public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
            {
                target.addPotionEffect(new PotionEffect(PotionInit.PARALYSIS, 60, 0));
                attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 0));
                return super.hitEntity(stack, target, attacker);
            }
        }.setTranslationKey("mjolnir").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "mjolnir");
        INFINITY_GAUNTLET = registerItem(new ItemGlove(2, 999.0F, -1.2F).setTranslationKey("infinityGauntlet").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "infinity_gauntlet");
    }

    /**
     * Register a item with a string id.
     *
     * @param item The item to register
     * @param name The item's string id
     */
    private static Item registerItem(Item item, String name)
    {
        ForgeRegistries.ITEMS.register(item.setRegistryName(name));
        return item;
    }

    /**
     * Register a default item for a block.
     *
     * @param block The block to register
     */
    private static ItemBlock registerItemBlock(Block block)
    {
        ItemBlock item = new ItemBlock(block);
        ForgeRegistries.ITEMS.register(item.setRegistryName(block.getRegistryName()));
        return item;
    }

    /**
     * Register a item for a block.
     *
     * @param block The block to register
     * @param item The block's item to register
     * @param name The item's string id
     */
    private static Item registerItemBlock(Block block, Item item, String name)
    {
        registerItem(item, name);
        GameData.getBlockItemMap().put(block, item);
        return item;
    }
}
