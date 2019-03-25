package io.github.dawncraft.item;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockSkullBase;
import io.github.dawncraft.api.item.*;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.potion.PotionLoader;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Register some items.
 *
 * @author QingChenW
 */
public class ItemLoader
{
    // Action
    public static final EnumAction SHOOT = EnumHelper.addAction("SHOOT");
    public static final EnumAction RELOAD = EnumHelper.addAction("RELOAD");
    
    // Tool

    // Energy
    public static Item bucketPetroleum = new ItemBucket(BlockLoader.fluidPetroleum).setUnlocalizedName("petroleumBucket").setCreativeTab(CreativeTabsLoader.tabEnergy).setContainerItem(Items.bucket);
    
    // Magnet
    public static Item magnet = new Item().setUnlocalizedName("magnet").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Item magnetIngot = new Item().setUnlocalizedName("magnetIngot").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Item magnetStick = new Item().setUnlocalizedName("magnetStick").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Item magnetBall = new ItemMagnetBall().setUnlocalizedName("magnetBall").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Item magnetCard = new ItemMagnetCard().setUnlocalizedName("magnetCard").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Item magnetDoor = new ItemMagnetDoor().setUnlocalizedName("magnetDoor").setCreativeTab(CreativeTabsLoader.tabMagnet);
    
    public static final Item.ToolMaterial MAGNET_TOOL = EnumHelper.addToolMaterial("MAGNET", 2, 285, 6.0F, 2.0F, 11).setRepairItem(new ItemStack(magnetIngot));
    public static final ItemArmor.ArmorMaterial MAGNET_ARMOR = EnumHelper.addArmorMaterial("MAGNET", Dawncraft.MODID + ":" + "magnet", 17, new int[] { 1, 5, 4, 2 }, 11);
    public static Item magnetAxe = new ItemAxe(MAGNET_TOOL).setUnlocalizedName("magnetAxe");
    public static Item magnetPickaxe = new ItemPickaxe(MAGNET_TOOL).setUnlocalizedName("magnetPickaxe");
    public static Item magnetHammer = new ItemHammer(MAGNET_TOOL).setUnlocalizedName("magnetHammer");
    public static Item magnetSpade = new ItemSpade(MAGNET_TOOL).setUnlocalizedName("magnetSpade");
    public static Item magnetHoe = new ItemHoe(MAGNET_TOOL).setUnlocalizedName("magnetHoe");
    public static Item magnetSword = new ItemSword(MAGNET_TOOL).setUnlocalizedName("magnetSword");
    public static Item magnetWand = new ItemWand(MAGNET_TOOL, 0.20F).setUnlocalizedName("magnetWand");
    public static Item magnetHelmet = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 0).setUnlocalizedName("magnetHelmet");
    public static Item magnetChestplate = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 1).setUnlocalizedName("magnetChestplate");
    public static Item magnetLeggings = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 2).setUnlocalizedName("magnetLeggings");
    public static Item magnetBoots = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 3).setUnlocalizedName("magnetBoots");
    
    // Machine
    public static Item copperIngot = new Item().setUnlocalizedName("copperIngot").setCreativeTab(CreativeTabsLoader.tabMachine);
    
    // Computer
    public static Item simpleCPU = new Item().setUnlocalizedName("simpleCPU").setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Item advancedCPU = new Item().setUnlocalizedName("advancedCPU").setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Item superCPU = new Item().setUnlocalizedName("superCPU").setCreativeTab(CreativeTabsLoader.tabComputer);
    
    // Science

    // Furniture

    // Cuisine
    public static Item bottle = new Item().setUnlocalizedName("bottle").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item faeces = (ItemFood) new ItemFood(1, 0.0F, true)
    {
        @Override
        public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
        {
            if (!worldIn.isRemote)
            {
                player.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 1));
                player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 1));
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 1));
            }
            super.onFoodEaten(stack, worldIn, player);
        }
    }.setAlwaysEdible().setUnlocalizedName("faeces").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item cookedEgg = new ItemFood(2, 0.2F, false).setUnlocalizedName("eggCooked").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item honeyChicken = new ItemFood(8, 0.6F, true).setUnlocalizedName("chickenHoney").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item honeyStew = new ItemSoup(2).setUnlocalizedName("honeyStew").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item frogStew = new ItemSoup(4).setUnlocalizedName("frogStew").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item honey = new Item().setUnlocalizedName("honey").setCreativeTab(CreativeTabsLoader.tabCuisine);
    public static Item frog = new Item().setUnlocalizedName("frog").setCreativeTab(CreativeTabsLoader.tabCuisine);
    
    // Guns
    public static Item gunAK47 = new ItemGunRifle(423, 30, 69, 2, 1, 0.85F, 0.70F, 0.65F, 6.0F).setUnlocalizedName("gunAK47").setCreativeTab(CreativeTabsLoader.tabWar);
    public static Item gunRPG = new ItemGunLauncher(28, 3, 100, -1, 1, 0.5F, 0.5F, 0.95F).setUnlocalizedName("gunRPG").setCreativeTab(CreativeTabsLoader.tabWar);
    public static Item gunBullet = new Item().setUnlocalizedName("gunBullet").setCreativeTab(CreativeTabsLoader.tabWar);
    public static Item gunRocket = new Item().setUnlocalizedName("gunRocket").setCreativeTab(CreativeTabsLoader.tabWar).setMaxStackSize(16);
    
    // Magic
    public static Item magicDust = new Item().setUnlocalizedName("magicDust").setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item skillBook = new ItemSkillBook().setUnlocalizedName("skillBook").setCreativeTab(CreativeTabsLoader.tabMagic);
    
    // ColourEgg
    public static Item skull = new ItemSkullBase(new String[] {"savage", "barbarianking", "gerking"})
    {
        @Override
        public BlockSkullBase getSkullBlock()
        {
            return (BlockSkullBase) BlockLoader.skull;
        }
    }.setUnlocalizedName("skull").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item gerHeart = new ItemFood(2, 1.0F, false)
    {
        @Override
        public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
        {
            if (!worldIn.isRemote)
            {
                player.addPotionEffect(new PotionEffect(PotionLoader.potionGerPower.id, 400, 2));
                player.addExperience(2000);
            }
            super.onFoodEaten(stack, worldIn, player);
        }
    }.setAlwaysEdible().setUnlocalizedName("gerHeart").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item brainDead = new ItemFood(2, 8.0F, false)
    {
        @Override
        public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
        {
            if (!worldIn.isRemote)
            {
                player.addPotionEffect(new PotionEffect(PotionLoader.potionBrainDead.id, 160, 1));
                player.addExperience(29);
            }
            super.onFoodEaten(stack, worldIn, player);
        }
    }.setAlwaysEdible().setUnlocalizedName("brainDead").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item funny = new Item().setUnlocalizedName("funny").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    
    public static Item dj = new ItemRecordDawn("dj").setUnlocalizedName("record").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item wz = new ItemRecordDawn("wzsongs").setUnlocalizedName("record").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    
    public static final Item.ToolMaterial GOLDIAMOND = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F, 16);
    public static final Item.ToolMaterial MJOLNIR = EnumHelper.addToolMaterial("MJOLNIR", 4, 2586, 10.0F, 2.0F, 24);
    public static Item goldiamondSword = new ItemSword(ItemLoader.GOLDIAMOND).setUnlocalizedName("goldiamondSword").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item mjolnir = new ItemHammer(ItemLoader.MJOLNIR)
    {
        @Override
        public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
        {
            target.addPotionEffect(new PotionEffect(PotionLoader.potionParalysis.getId(), 60, 0));
            attacker.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 60, 0));

            return super.hitEntity(stack, target, attacker);
        }
    }.setUnlocalizedName("mjolnir").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    
    public static void initItems()
    {
        // Energy
        registerItem(bucketPetroleum, "petroleum_bucket");
        FluidContainerRegistry.registerFluidContainer(FluidLoader.fluidPetroleum, new ItemStack(ItemLoader.bucketPetroleum), FluidContainerRegistry.EMPTY_BUCKET);
        
        // Magnet
        registerItem(magnet, "magnet");
        registerItem(magnetIngot, "magnet_ingot");
        registerItem(magnetStick, "magnet_stick");
        registerItem(magnetBall, "magnet_ball");
        registerItem(magnetCard, "magnet_card");
        registerItem(magnetDoor, "magnet_door");
        
        registerItem(magnetAxe, "magnet_axe");
        registerItem(magnetPickaxe, "magnet_pickaxe");
        registerItem(magnetHammer, "magnet_hammer");
        registerItem(magnetSpade, "magnet_shovel");
        registerItem(magnetHoe, "magnet_hoe");
        registerItem(magnetSword, "magnet_sword");
        registerItem(magnetWand, "magnet_wand");
        registerItem(magnetHelmet, "magnet_helmet");
        registerItem(magnetChestplate, "magnet_chestplate");
        registerItem(magnetLeggings, "magnet_leggings");
        registerItem(magnetBoots, "magnet_boots");
        
        // Machine
        registerItem(copperIngot, "copper_ingot");
        
        // Computer
        registerItem(simpleCPU, "simple_CPU");
        registerItem(advancedCPU, "advanced_CPU");
        registerItem(superCPU, "super_CPU");
        
        // Science
        
        // Furniture
        
        // Cuisine
        registerItem(bottle, "bottle");
        registerItem(faeces, "faeces");
        registerItem(cookedEgg, "cooked_egg");
        registerItem(honeyChicken, "honey_chicken");
        registerItem(honeyStew, "honey_stew");
        registerItem(frogStew, "frog_stew");
        registerItem(honey, "honey");
        registerItem(frog, "frog");
        
        // War
        registerItem(gunAK47, "gun_ak47");
        registerItem(gunBullet, "gun_bullet");
        registerItem(gunRPG, "gun_rpg");
        registerItem(gunRocket, "gun_rocket");
        
        // Magic
        registerItem(magicDust, "magic_dust");
        registerItem(skillBook, "skill_book");
        
        // ColourEgg
        registerItem(skull, "skull");
        registerItem(gerHeart, "ger_heart");
        registerItem(brainDead, "brain_dead");
        registerItem(funny, "funny");
        
        registerItem(dj, "record_dj");
        registerItem(wz, "record_wzsongs");
        
        registerItem(goldiamondSword, "goldiamond_sword");
        registerItem(mjolnir, "mjolnir");
        
        // Tools, Weapons and Armors
        
    }

    /**
     * Register a item with a string id.
     *
     * @param item The item to be registered
     * @param name The item's string id
     */
    private static void registerItem(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
    }
}
