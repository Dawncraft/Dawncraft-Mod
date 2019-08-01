package io.github.dawncraft.item;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ItemAxe;
import io.github.dawncraft.api.item.ItemGlove;
import io.github.dawncraft.api.item.ItemGunLauncher;
import io.github.dawncraft.api.item.ItemGunRifle;
import io.github.dawncraft.api.item.ItemHammer;
import io.github.dawncraft.api.item.ItemWand;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.block.BlockSkull;
import io.github.dawncraft.client.sound.SoundInit;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.potion.PotionInit;
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
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Register some items.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
@ObjectHolder(Dawncraft.MODID)
public class ItemInit
{
    // Action
    public static EnumAction SHOOT = EnumHelper.addAction("SHOOT");
    public static EnumAction RELOAD = EnumHelper.addAction("RELOAD");

    public static Item.ToolMaterial MAGNET_TOOL = EnumHelper.addToolMaterial("MAGNET", 2, 285, 6.0F, 2.0F, 11);
    public static ItemArmor.ArmorMaterial MAGNET_ARMOR = EnumHelper.addArmorMaterial("MAGNET", Dawncraft.MODID + ":" + "magnet", 17, new int[] { 1, 5, 4, 2 }, 11, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static ItemArmor.ArmorMaterial COPPER_ARMOR = EnumHelper.addArmorMaterial("COPPER", Dawncraft.MODID + ":" + "copper", 13, new int[] { 1, 4, 3, 1 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    // Redstone
    public static final Item MAGNET_CARD = null;
    public static final Item MAGNET_DOOR = null;

    // Materials/Misc
    public static final Item MAGNET = null;
    public static final Item MAGNET_INGOT = null;
    public static final Item MAGNET_STICK = null;
    public static final Item MAGNET_BALL = null;

    public static final Item COPPER_INGOT = null;

    // Tools
    public static final Item MAGNET_AXE = null;
    public static final Item MAGNET_PICKAXE = null;
    public static final Item MAGNET_HAMMER = null;
    public static final Item MAGNET_SPADE = null;
    public static final Item MAGNET_HOE = null;

    // Compat
    public static final Item MAGNET_SWORD = null;
    public static final Item MAGNET_WAND = null;
    public static final Item MAGNET_HELMET = null;
    public static final Item MAGNET_CHESTPLATE = null;
    public static final Item MAGNET_LEGGINGS = null;
    public static final Item MAGNET_BOOTS = null;

    // Science

    // Energy

    // Machine

    // Computer
    public static final Item SIMPLE_CPU = null;
    public static final Item ADVANCED_CPU = null;
    public static final Item PROFESSIONAL_CPU = null;

    // Furniture

    // Cuisine
    public static final Item TUMBLER = null;
    public static final Item FAECES = null;
    public static final Item COOKED_EGG = null;
    public static final Item HONEY_CHICKEN = null;
    public static final Item HONEY_STEW = null;
    public static final Item FROG_STEW = null;
    public static final Item HONEY = null;
    public static final Item FROG = null;

    // Weapons
    public static final Item GUN_AK47 = null;
    public static final Item GUN_RPG = null;
    public static final Item GUN_BULLET = null;
    public static final Item GUN_ROCKET = null;
    public static final Item THROWABLE_TORCH = null;

    // Magic
    public static final Item MAGIC_DUST = null;
    public static final Item SKILL_BOOK = null;

    // ColourEgg
    public static Item.ToolMaterial GOLDIAMOND_TOOL = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F, 16);
    public static Item.ToolMaterial MJOLNIR_TOOL = EnumHelper.addToolMaterial("MJOLNIR", 4, 2586, 10.0F, 2.0F, 24);

    public static final Item SKULL = null;
    public static final Item GER_HEART = null;
    public static final Item BRAIN_DEAD = null;
    public static final Item FUNNY = null;

    public static final Item RECORD_DJ = null;
    public static final Item RECORD_WZSONGS = null;

    public static final Item GOLDIAMOND_SWORD = null;
    public static final Item MJOLNIR = null;
    public static final Item INFINITY_GAUNTLET = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        // Materials/Misc
        registerItem(new Item().setTranslationKey("magnet").setCreativeTab(CreativeTabs.MATERIALS), "magnet");
        Item item = new Item().setTranslationKey("magnetIngot").setCreativeTab(CreativeTabs.MATERIALS);
        registerItem(item, "magnet_ingot");
        MAGNET_TOOL.setRepairItem(new ItemStack(item));
        registerItem(new Item().setTranslationKey("magnetStick").setCreativeTab(CreativeTabs.MATERIALS), "magnet_stick");
        registerItem(new ItemMagnetBall().setTranslationKey("magnetBall").setCreativeTab(CreativeTabs.MISC), "magnet_ball");

        registerItem(new Item().setTranslationKey("copperIngot").setCreativeTab(CreativeTabs.MATERIALS), "copper_ingot");

        // Redstone
        registerItem(new ItemMagnetCard().setTranslationKey("magnetCard").setCreativeTab(CreativeTabs.REDSTONE), "magnet_card");
        registerItem(new ItemMagnetDoor().setTranslationKey("magnetDoor").setCreativeTab(CreativeTabs.REDSTONE), "magnet_door");

        // Tools
        registerItem(new ItemHammer(ToolMaterial.WOOD).setTranslationKey("woodHammer"), "wood_hammer");
        registerItem(new ItemHammer(ToolMaterial.STONE).setTranslationKey("stoneHammer"), "stone_hammer");
        registerItem(new ItemHammer(ToolMaterial.IRON).setTranslationKey("ironHammer"), "iron_hammer");
        registerItem(new ItemHammer(ToolMaterial.GOLD).setTranslationKey("goldHammer"), "gold_hammer");
        registerItem(new ItemHammer(ToolMaterial.DIAMOND).setTranslationKey("diamondHammer"), "diamond_hammer");
        registerItem(new ItemAxe(MAGNET_TOOL, 8.0F,-3.2F).setTranslationKey("magnetAxe"), "magnet_axe");
        registerItem(new ItemPickaxe(MAGNET_TOOL).setTranslationKey("magnetPickaxe"), "magnet_pickaxe");
        registerItem(new ItemSpade(MAGNET_TOOL).setTranslationKey("magnetSpade"), "magnet_shovel");
        registerItem(new ItemHoe(MAGNET_TOOL).setTranslationKey("magnetHoe"), "magnet_hoe");
        registerItem(new ItemHammer(MAGNET_TOOL).setTranslationKey("magnetHammer"), "magnet_hammer");

        // Combat
        registerItem(new ItemSword(MAGNET_TOOL).setTranslationKey("magnetSword"), "magnet_sword");
        registerItem(new ItemWand(MAGNET_TOOL, 0.20F).setTranslationKey("magnetWand"), "magnet_wand");
        registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.HEAD).setTranslationKey("magnetHelmet"), "magnet_helmet");
        registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.CHEST).setTranslationKey("magnetChestplate"), "magnet_chestplate");
        registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.LEGS).setTranslationKey("magnetLeggings"), "magnet_leggings");
        registerItem(new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), EntityEquipmentSlot.FEET).setTranslationKey("magnetBoots"), "magnet_boots");

        // Science

        // Energy

        // Machine


        // Computer
        registerItem(new Item().setTranslationKey("simpleCPU").setCreativeTab(CreativeTabsLoader.tabComputer), "simple_cpu");
        registerItem(new Item().setTranslationKey("advancedCPU").setCreativeTab(CreativeTabsLoader.tabComputer), "advanced_cpu");
        registerItem(new Item().setTranslationKey("professionalCPU").setCreativeTab(CreativeTabsLoader.tabComputer), "professional_cpu");

        // Furniture

        // Cuisine
        registerItem(new Item().setTranslationKey("tumbler").setCreativeTab(CreativeTabsLoader.tabCuisine), "tumbler");
        registerItem(new ItemFood(1, 0.0F, true)
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
        }.setAlwaysEdible().setTranslationKey("faeces").setCreativeTab(CreativeTabsLoader.tabCuisine), "faeces");
        registerItem(new Item().setTranslationKey("honey").setCreativeTab(CreativeTabsLoader.tabCuisine), "honey");
        registerItem(new Item().setTranslationKey("frog").setCreativeTab(CreativeTabsLoader.tabCuisine), "frog");
        registerItem(new ItemFood(2, 0.2F, false).setTranslationKey("eggCooked").setCreativeTab(CreativeTabsLoader.tabCuisine), "cooked_egg");
        registerItem(new ItemFood(8, 0.6F, true).setTranslationKey("chickenHoney").setCreativeTab(CreativeTabsLoader.tabCuisine), "honey_chicken");
        registerItem(new ItemSoup(2).setTranslationKey("honeyStew").setCreativeTab(CreativeTabsLoader.tabCuisine), "honey_stew");
        registerItem(new ItemSoup(4).setTranslationKey("frogStew").setCreativeTab(CreativeTabsLoader.tabCuisine), "frog_stew");

        // Weapons
        registerItem(new ItemGunRifle(423, 30, 69, 2, 1, 0.85F, 0.70F, 0.65F, 6.0F).setTranslationKey("gunAK47").setCreativeTab(CreativeTabsLoader.tabWeapons), "gun_ak47");
        registerItem(new Item().setTranslationKey("gunBullet").setCreativeTab(CreativeTabsLoader.tabWeapons), "gun_bullet");
        registerItem(new ItemGunLauncher(28, 3, 100, -1, 1, 0.5F, 0.5F, 0.95F).setTranslationKey("gunRPG").setCreativeTab(CreativeTabsLoader.tabWeapons), "gun_rpg");
        registerItem(new Item().setTranslationKey("gunRocket").setCreativeTab(CreativeTabsLoader.tabWeapons).setMaxStackSize(16), "gun_rocket");
        registerItem(new ItemThrowableTorch().setTranslationKey("throwableTorch").setCreativeTab(CreativeTabsLoader.tabWeapons), "throwable_torch");

        // Magic
        registerItem(new Item().setTranslationKey("magicDust").setCreativeTab(CreativeTabsLoader.tabMagic), "magic_dust");
        registerItem(new ItemSkillBook().setTranslationKey("skillBook").setCreativeTab(CreativeTabsLoader.tabMagic), "skill_book");

        // ColourEgg
        registerItem(new ItemSkull()
        {
            @Override
            public BlockSkull getSkullBlock()
            {
                return (BlockSkull) BlockInit.SKULL;
            }
        }.setTranslationKey("skull").setCreativeTab(CreativeTabsLoader.tabColourEgg), "skull");
        registerItem(new ItemFood(2, 1.0F, false)
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
        }.setAlwaysEdible().setTranslationKey("gerHeart").setCreativeTab(CreativeTabsLoader.tabColourEgg), "ger_heart");
        registerItem(new ItemFood(2, 8.0F, false)
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
        }.setAlwaysEdible().setTranslationKey("brainDead").setCreativeTab(CreativeTabsLoader.tabColourEgg), "brain_dead");
        registerItem(new Item().setTranslationKey("funny").setCreativeTab(CreativeTabsLoader.tabColourEgg), "funny");

        registerItem(new ItemRecord("dj", SoundInit.RECORDS_DJ).setTranslationKey("record").setCreativeTab(CreativeTabsLoader.tabColourEgg), "record_dj");
        registerItem(new ItemRecord("wzsongs", SoundInit.RECORDS_WZSONGS).setTranslationKey("record").setCreativeTab(CreativeTabsLoader.tabColourEgg), "record_wzsongs");

        registerItem(new ItemSword(ItemInit.GOLDIAMOND_TOOL).setTranslationKey("goldiamondSword").setCreativeTab(CreativeTabsLoader.tabColourEgg), "goldiamond_sword");
        registerItem(new ItemHammer(ItemInit.MJOLNIR_TOOL)
        {
            @Override
            public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
            {
                target.addPotionEffect(new PotionEffect(PotionInit.PARALYSIS, 60, 0));
                attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 0));
                return super.hitEntity(stack, target, attacker);
            }
        }.setTranslationKey("mjolnir").setCreativeTab(CreativeTabsLoader.tabColourEgg), "mjolnir");
        registerItem(new ItemGlove(2, 999.0F, -1.2F).setTranslationKey("infinityGauntlet").setCreativeTab(CreativeTabsLoader.tabColourEgg), "infinity_gauntlet");
    }

    /**
     * Register a item with a string id.
     *
     * @param item The item to register
     * @param name The item's string id
     */
    private static void registerItem(Item item, String name)
    {
        ForgeRegistries.ITEMS.register(item.setRegistryName(name));
    }
}
