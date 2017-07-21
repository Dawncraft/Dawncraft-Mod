package io.github.dawncraft.item;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.api.block.BlockSkullBase;
import io.github.dawncraft.api.item.*;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.potion.PotionLoader;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author QingChenW
 *
 */
public class ItemLoader
{
    // Energy
    public static Item bucketPetroleum = new ItemBucket(BlockLoader.fluidPetroleum)
            .setUnlocalizedName("petroleumBucket").setCreativeTab(CreativeTabsLoader.tabEnergy)
            .setContainerItem(Items.bucket);
    
    // Magnetism
    public static Item magnet = new Item().setUnlocalizedName("magnet").setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Item magnetIngot = new Item().setUnlocalizedName("magnetIngot")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Item magnetStick = new Item().setUnlocalizedName("magnetStick")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Item magnetBall = new ItemMagnetBall().setUnlocalizedName("magnetBall")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Item magnetCard = new ItemMagnetCard().setUnlocalizedName("magnetCard")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Item magnetDoor = new ItemMagnetDoor().setUnlocalizedName("magnetDoor")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    
    public static final Item.ToolMaterial MAGNET_TOOL = EnumHelper.addToolMaterial("MAGNET", 2, 285, 6.0F, 2.0F, 11)
            .setRepairItem(new ItemStack(magnetIngot));
    public static final ItemArmor.ArmorMaterial MAGNET_ARMOR = EnumHelper.addArmorMaterial("MAGNET",
            dawncraft.MODID + ":" + "magnet", 17, new int[]
                    { 1, 5, 4, 2 }, 11);
    public static Item magnetAxe = new ItemAxeBase(MAGNET_TOOL).setUnlocalizedName("magnetAxe");
    public static Item magnetPickaxe = new ItemPickaxeBase(MAGNET_TOOL).setUnlocalizedName("magnetPickaxe");
    public static Item magnetHammer = new ItemHammerBase(MAGNET_TOOL).setUnlocalizedName("magnetHammer");
    public static Item magnetSpade = new ItemSpadeBase(MAGNET_TOOL).setUnlocalizedName("magnetSpade");
    public static Item magnetHoe = new ItemHoeBase(MAGNET_TOOL).setUnlocalizedName("magnetHoe");
    public static Item magnetSword = new ItemSword(MAGNET_TOOL).setUnlocalizedName("magnetSword");
    public static Item magnetWand = new ItemWandBase(MAGNET_TOOL, 0.8F).setUnlocalizedName("magnetWand");
    public static Item magnetHelmet = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 0)
            .setUnlocalizedName("magnetHelmet");
    public static Item magnetChestplate = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 1)
            .setUnlocalizedName("magnetChestplate");
    public static Item magnetLeggings = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 2)
            .setUnlocalizedName("magnetLeggings");
    public static Item magnetBoots = new ItemArmor(MAGNET_ARMOR, MAGNET_ARMOR.ordinal(), 3)
            .setUnlocalizedName("magnetBoots");
    
    // Machine
    
    // Computer
    public static Item simpleCPU = new Item().setUnlocalizedName("simpleCPU")
            .setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Item advancedCPU = new Item().setUnlocalizedName("advancedCPU")
            .setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Item superCPU = new Item().setUnlocalizedName("superCPU")
            .setCreativeTab(CreativeTabsLoader.tabComputer);
    
    // Science
    
    // Furniture
    
    // Food
    public static ItemFood faeces = (ItemFood) new ItemFood(1, 0.0F, true)
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
    }.setAlwaysEdible().setUnlocalizedName("faeces").setCreativeTab(CreativeTabsLoader.tabFood);
    public static Item cakeEgg = new ItemFood(4, 6.0F, false).setUnlocalizedName("cakeEgg")
            .setCreativeTab(CreativeTabsLoader.tabFood);
    
    // Flans
    public static Item flanAK47 = new ItemFlanAK47(423).setUnlocalizedName("flanAK47")
            .setCreativeTab(CreativeTabsLoader.tabFlans);
    public static Item flanBullet = new Item().setUnlocalizedName("flanBullet")
            .setCreativeTab(CreativeTabsLoader.tabFlans);
    public static Item flanRPG = new ItemFlanRPG(28).setUnlocalizedName("flanRPG")
            .setCreativeTab(CreativeTabsLoader.tabFlans);
    public static Item flanRocket = new Item().setUnlocalizedName("flanRocket")
            .setCreativeTab(CreativeTabsLoader.tabFlans).setMaxStackSize(16);
    
    // Magic
    public static Item magicDust = new Item().setUnlocalizedName("magicDust")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item magicBook = new ItemMagicBook().setUnlocalizedName("magicBook")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item metalEssence = new Item().setUnlocalizedName("metalEssence")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item woodEssence = new Item().setUnlocalizedName("woodEssence")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item waterEssence = new Item().setUnlocalizedName("waterEssence")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item fireEssence = new Item().setUnlocalizedName("fireEssence")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item dirtEssence = new Item().setUnlocalizedName("dirtEssence")
            .setCreativeTab(CreativeTabsLoader.tabMagic);
    
    // ColourEgg
    public static Item skull = new ItemSkullBase((BlockSkullBase) BlockLoader.skull, new String[] {"savage", "barbarianking", "gerking"})
            .setUnlocalizedName("skull").setCreativeTab(CreativeTabsLoader.tabColourEgg);
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
    
    public static Item chinese = new ItemRecordBase(dawncraft.MODID, "chinese").setUnlocalizedName("record")
            .setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item dj = new ItemRecordBase(dawncraft.MODID, "dj").setUnlocalizedName("record")
            .setCreativeTab(CreativeTabsLoader.tabColourEgg);
    
    public static final Item.ToolMaterial GOLDIAMOND = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F,
            16);
    public static final Item.ToolMaterial MJOLNIR = EnumHelper.addToolMaterial("MJOLNIR", 4, 2586, 10.0F, 2.0F, 24);
    public static Item goldiamondSword = new ItemSword(ItemLoader.GOLDIAMOND).setUnlocalizedName("goldiamondSword")
            .setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Item mjolnir = new ItemHammerBase(MJOLNIR)
    {
        @Override
        public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
        {
            target.addPotionEffect(new PotionEffect(PotionLoader.potionParalysis.getId(), 60, 0));
            attacker.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 60, 0));

            return super.hitEntity(stack, target, attacker);
        }
    }.setUnlocalizedName("mjolnir").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    
    public ItemLoader(FMLPreInitializationEvent event)
    {
        // Energy
        register(bucketPetroleum, "petroleum_bucket");
        FluidContainerRegistry.registerFluidContainer(FluidLoader.fluidPetroleum,
                new ItemStack(ItemLoader.bucketPetroleum), FluidContainerRegistry.EMPTY_BUCKET);
        
        // Magnet
        register(magnet, "magnet");
        register(magnetIngot, "magnet_ingot");
        register(magnetStick, "magnet_stick");
        register(magnetBall, "magnet_ball");
        register(magnetCard, "magnet_card");
        register(magnetDoor, "magnet_door");
        
        register(magnetAxe, "magnet_axe");
        register(magnetPickaxe, "magnet_pickaxe");
        register(magnetHammer, "magnet_hammer");
        register(magnetSpade, "magnet_shovel");
        register(magnetHoe, "magnet_hoe");
        register(magnetSword, "magnet_sword");
        register(magnetWand, "magnet_wand");
        register(magnetHelmet, "magnet_helmet");
        register(magnetChestplate, "magnet_chestplate");
        register(magnetLeggings, "magnet_leggings");
        register(magnetBoots, "magnet_boots");
        
        // Machine
        
        // Computer
        register(simpleCPU, "simple_CPU");
        register(advancedCPU, "advanced_CPU");
        register(superCPU, "super_CPU");
        
        // Materials
        
        // Furniture
        
        // Food
        register(faeces, "faeces");
        register(cakeEgg, "cake_egg");
        
        // Magic
        register(magicDust, "magic_dust");
        register(magicBook, "magic_book");
        register(metalEssence, "metal_essence");
        register(woodEssence, "wood_essence");
        register(waterEssence, "water_essence");
        register(fireEssence, "fire_essence");
        register(dirtEssence, "dirt_essence");
        
        // Flans
        register(flanAK47, "flan_ak47");
        register(flanBullet, "flan_bullet");
        register(flanRPG, "flan_rpg");
        register(flanRocket, "flan_rocket");
        
        // ColourEgg
        register(skull, "skull");
        register(gerHeart, "ger_heart");
        register(brainDead, "brain_dead");
        register(funny, "funny");
        
        register(chinese, "record_chinese");
        register(dj, "record_dj");
        
        register(goldiamondSword, "goldiamond_sword");
        register(mjolnir, "mjolnir");
        
        // Tools, Weapons and Armors

    }
    
    /**
     * Register a item with a name-id.
     *
     * @param item
     *            The item to register
     * @param name
     *            The item's name-id
     */
    private static void register(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
    }
}
