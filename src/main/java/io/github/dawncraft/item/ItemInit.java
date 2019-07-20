package io.github.dawncraft.item;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ItemGunLauncher;
import io.github.dawncraft.api.item.ItemGunRifle;
import io.github.dawncraft.api.item.ItemHammer;
import io.github.dawncraft.api.item.ItemWand;
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
import net.minecraft.item.ItemAxe;
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
    public static final EnumAction SHOOT = EnumHelper.addAction("SHOOT");
    public static final EnumAction RELOAD = EnumHelper.addAction("RELOAD");

    public static final Item.ToolMaterial MAGNET_TOOL = EnumHelper.addToolMaterial("MAGNET", 2, 285, 6.0F, 2.0F, 11);
    public static final ItemArmor.ArmorMaterial MAGNET_ARMOR = EnumHelper.addArmorMaterial("MAGNET", Dawncraft.MODID + ":" + "magnet", 17, new int[] { 1, 5, 4, 2 }, 11, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    // Redstone
    public static final Item magnetCard = null;
    public static final Item magnetDoor = null;

    // Materials/Misc
    public static final Item magnet = null;
    public static final Item magnetIngot = null;
    public static final Item magnetStick = null;
    public static final Item magnetBall = null;

    public static final Item copperIngot = null;

    // Tools
    public static final Item magnetAxe = null;
    public static final Item magnetPickaxe = null;
    public static final Item magnetHammer = null;
    public static final Item magnetSpade = null;
    public static final Item magnetHoe = null;

    // Compat
    public static final Item magnetSword = null;
    public static final Item magnetWand = null;
    public static final Item magnetHelmet = null;
    public static final Item magnetChestplate = null;
    public static final Item magnetLeggings = null;
    public static final Item magnetBoots = null;

    // Science

    // Energy

    // Machine

    // Computer
    public static final Item simpleCPU = null;
    public static final Item advancedCPU = null;
    public static final Item superCPU = null;

    // Furniture

    // Cuisine
    public static final Item tumbler = null;
    public static final Item faeces = null;
    public static final Item cookedEgg = null;
    public static final Item honeyChicken = null;
    public static final Item honeyStew = null;
    public static final Item frogStew = null;
    public static final Item honey = null;
    public static final Item frog = null;

    // Weapons
    public static final Item gunAK47 = null;
    public static final Item gunRPG = null;
    public static final Item gunBullet = null;
    public static final Item gunRocket = null;
    public static final Item throwableTorch = null;

    // Magic
    public static final Item magicDust = null;
    public static final Item skillBook = null;

    // ColourEgg
    public static final Item.ToolMaterial GOLDIAMOND = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F, 16);
    public static final Item.ToolMaterial MJOLNIR = EnumHelper.addToolMaterial("MJOLNIR", 4, 2586, 10.0F, 2.0F, 24);

    public static final Item skull = null;
    public static final Item gerHeart = null;
    public static final Item brainDead = null;
    public static final Item funny = null;

    public static final Item dj = null;
    public static final Item wz = null;

    public static final Item goldiamondSword = null;
    public static final Item mjolnir = null;

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
        registerItem(new ItemAxe(MAGNET_TOOL).setTranslationKey("magnetAxe"), "magnet_axe");
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
        registerItem(new Item().setTranslationKey("superCPU").setCreativeTab(CreativeTabsLoader.tabComputer), "super_cpu");

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
                    player.addPotionEffect(new PotionEffect(PotionInit.potionConfusion, 200, 1));
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
        registerItem(new ItemSkullDawn().setTranslationKey("skull").setCreativeTab(CreativeTabsLoader.tabColourEgg), "skull");
        registerItem(new ItemFood(2, 1.0F, false)
        {
            @Override
            public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
            {
                super.onFoodEaten(stack, world, player);
                if (!world.isRemote)
                {
                    player.addPotionEffect(new PotionEffect(PotionInit.potionGerPower, 400, 2));
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
                    player.addPotionEffect(new PotionEffect(PotionInit.potionBrainDead, 160, 1));
                    player.addExperience(233);
                }
            }
        }.setAlwaysEdible().setTranslationKey("brainDead").setCreativeTab(CreativeTabsLoader.tabColourEgg), "brain_dead");
        registerItem(new Item().setTranslationKey("funny").setCreativeTab(CreativeTabsLoader.tabColourEgg), "funny");

        registerItem(new ItemRecord("dj", SoundInit.RECORDS_DJ).setTranslationKey("record").setCreativeTab(CreativeTabsLoader.tabColourEgg), "record_dj");
        registerItem(new ItemRecord("wzsongs", SoundInit.RECORDS_WZSONGS).setTranslationKey("record").setCreativeTab(CreativeTabsLoader.tabColourEgg), "record_wzsongs");

        registerItem(new ItemSword(ItemInit.GOLDIAMOND).setTranslationKey("goldiamondSword").setCreativeTab(CreativeTabsLoader.tabColourEgg), "goldiamond_sword");
        registerItem( new ItemHammer(ItemInit.MJOLNIR)
        {
            @Override
            public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
            {
                target.addPotionEffect(new PotionEffect(PotionInit.potionParalysis, 60, 0));
                attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 0));
                return super.hitEntity(stack, target, attacker);
            }
        }.setTranslationKey("mjolnir").setCreativeTab(CreativeTabsLoader.tabColourEgg), "mjolnir");
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
