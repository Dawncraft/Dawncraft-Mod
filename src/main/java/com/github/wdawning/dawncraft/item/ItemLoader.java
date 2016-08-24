package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader
{
    //Item
    public static Item magnet = new Item().setUnlocalizedName("magnet").setCreativeTab(CreativeTabsLoader.tabMagnetic);
    public static Item magnetIngot = new Item().setUnlocalizedName("magnetIngot").setCreativeTab(CreativeTabsLoader.tabMagnetic);
    public static Item magnetStick = new Item().setUnlocalizedName("magnetStick").setCreativeTab(CreativeTabsLoader.tabMagnetic);
    public static Item magnetBall = new ItemMagnetBall();
    public static Item magnetCard = new ItemMagnetCard();
    public static Item bucketPetroleum = new ItemBucketPetroleum(); 
    
    public static Item simpleCPU = new ItemComputerCPU.SimpleCPU(); 
    public static Item highCPU = new ItemComputerCPU.HighCPU(); 
    public static Item proCPU = new ItemComputerCPU.ProCPU(); 
    public static Item superCPU = new ItemComputerCPU.SuperCPU(); 
    
    public static Item magicBook = new ItemMagicBook();
    public static Item metalEssence = new Item().setUnlocalizedName("metalEssence").setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item woodEssence = new Item().setUnlocalizedName("woodEssence").setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item waterEssence = new Item().setUnlocalizedName("waterEssence").setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item fireEssence = new Item().setUnlocalizedName("fireEssence").setCreativeTab(CreativeTabsLoader.tabMagic);
    public static Item dirtEssence = new Item().setUnlocalizedName("dirtEssence").setCreativeTab(CreativeTabsLoader.tabMagic);
    
    public static Item funny = new Item().setUnlocalizedName("funny").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    
    public static Item chinese = new ItemRecord("chinese").setUnlocalizedName("record");
    public static Item dj = new ItemRecord("dj").setUnlocalizedName("record");
    
    //Food
    public static Item cakeEgg = new ItemFood(4, 6.0F, false).setUnlocalizedName("cakeEgg").setCreativeTab(CreativeTabsLoader.tabFood);
    
    public static Item faeces = new ItemFaeces();
    public static ItemFood gerHeart = new ItemGerHeart();
    public static ItemFood brainDead = new ItemBrainDead();
    //Tool
    public static final Item.ToolMaterial GOLDIAMOND = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F, 16);
    public static final Item.ToolMaterial MAGNET = EnumHelper.addToolMaterial("MAGNET", 2, 452, 6.0F, 2.0F, 10);
    public static final Item.ToolMaterial HAMMER = EnumHelper.addToolMaterial("HAMMER", 4, 2586, 10.0F, 2.0F, 24);
    public static ItemSword goldiamondSword = new ItemGoldiamondSword();
    public static ItemSword magnetSword = new ItemMagnetSword();
    public static Item mjolnir = new ItemHammerMjolnir();
    
    public static Item flanAK47 = new ItemFlanAK47();
    public static Item flanRPG = new ItemFlanRPG();
    public static Item flanRPGRocket = new Item().setUnlocalizedName("flanRPGRocket").setMaxStackSize(16).setCreativeTab(CreativeTabsLoader.tabFlans);
    
    public static final ItemArmor.ArmorMaterial MAGNET_ARMOR = EnumHelper.addArmorMaterial("MAGNET", dawncraft.MODID + ":" + "magnet", 11, new int[]{ 1, 5, 4, 2}, 10);
    public static ItemArmor magnetHelmet = new ItemMagnetArmor.Helmet();
    public static ItemArmor magnetChestplate = new ItemMagnetArmor.Chestplate();
    public static ItemArmor magnetLeggings = new ItemMagnetArmor.Leggings();
    public static ItemArmor magnetBoots = new ItemMagnetArmor.Boots();
    
    public ItemLoader(FMLPreInitializationEvent event)
    {
    	//Item
        register(magnet, "magnet");
        register(magnetIngot, "magnet_ingot");
        register(magnetStick, "magnet_stick");
        register(magnetBall, "magnet_ball");
        register(magnetCard, "magnet_card");
        register(bucketPetroleum, "petroleum_bucket");
        
        register(simpleCPU, "simple_CPU");
        register(highCPU, "high_CPU");
        register(proCPU, "pro_CPU");
        register(superCPU, "super_CPU");
        
        register(magicBook, "magic_book");
        register(metalEssence, "metal_essence");
        register(woodEssence, "wood_essence");
        register(waterEssence, "water_essence");
        register(fireEssence, "fire_essence");
        register(dirtEssence, "dirt_essence");
        
        register(funny, "funny");
        
        register(chinese, "record_chinese");
        register(dj, "record_dj");
        //Food
        register(cakeEgg, "cake_egg");
        
        register(faeces, "faeces");
        register(gerHeart, "ger_heart");
        register(brainDead, "brain_dead");
        //Tool
        register(goldiamondSword, "goldiamond_sword"); 
        register(magnetSword, "magnet_sword"); 
        register(mjolnir, "mjolnir");
        
        register(flanAK47, "flan_AK47");
        register(flanRPG, "flan_RPG");
        register(flanRPGRocket, "flan_RPG_rocket");
        
        register(magnetHelmet, "magnet_helmet");
        register(magnetChestplate, "magnet_chestplate");
        register(magnetLeggings, "magnet_leggings");
        register(magnetBoots, "magnet_boots");
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
    	//Item
        registerRender(magnet, "magnet");
        registerRender(magnetIngot, "magnet_ingot");
        registerRender(magnetStick, "magnet_stick");
        registerRender(magnetBall, "magnet_ball");
        registerRender(magnetCard, "magnet_card");
        registerRender(bucketPetroleum, "petroleum_bucket");
        
        registerRender(simpleCPU, "simple_CPU");
        registerRender(highCPU, "high_CPU");
        registerRender(proCPU, "pro_CPU");
        registerRender(superCPU, "super_CPU");
        
        registerRender(magicBook, "magic_book");
        registerRender(metalEssence, "metal_essence");
        registerRender(woodEssence, "wood_essence");
        registerRender(waterEssence, "water_essence");
        registerRender(fireEssence, "fire_essence");
        registerRender(dirtEssence, "dirt_essence");
        
        registerRender(funny, "funny");
        
        registerRender(chinese, "record_chinese");
        registerRender(dj, "record_dj");
        //Food
        registerRender(cakeEgg, "cake_egg");
        
        registerRender(faeces, "faeces");
        registerRender(gerHeart, "ger_heart");
        registerRender(brainDead, "brain_dead");
        //Tool
        registerRender(goldiamondSword, "goldiamond_sword"); 
        registerRender(magnetSword, "magnet_sword"); 
        registerRender(mjolnir, "mjolnir");
        
        registerRender(flanAK47, "flan_AK47");
        registerRender(flanRPG, "flan_RPG");
        registerRender(flanRPGRocket, "flan_RPG_rocket");
        
        registerRender(magnetHelmet, "magnet_helmet");
        registerRender(magnetChestplate, "magnet_chestplate");
        registerRender(magnetLeggings, "magnet_leggings");
        registerRender(magnetBoots, "magnet_boots");
    }
    
    private static void register(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(dawncraft.MODID + ":" + name, "inventory"));
    }
}