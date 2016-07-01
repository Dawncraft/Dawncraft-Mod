package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader
{
    //Item
    public static Item magnet = new ItemMagnet();
    public static Item magnetIngot = new ItemMagnetIngot();
    public static Item magnetStick = new ItemMagnetStick();
    public static Item magnetBall = new ItemMagnetBall();
    public static Item magnetCard = new ItemMagnetCard();
    public static Item bucketPetroleum = new ItemBucketPetroleum(); 
    
    public static Item simpleCPU = new ItemComputerCPU.SimpleCPU(); 
    public static Item highCPU = new ItemComputerCPU.HighCPU(); 
    public static Item proCPU = new ItemComputerCPU.ProCPU(); 
    public static Item superCPU = new ItemComputerCPU.SuperCPU(); 
    //Food
    public static ItemFood cakeEgg = new ItemCakeEgg();
    
    public static ItemFood faeces = new ItemFaeces();
    public static ItemFood gerHeart = new ItemGerHeart();
    public static ItemFood brainDead = new ItemBrainDead();
    //Tool
    public static final Item.ToolMaterial GOLDIAMOND = EnumHelper.addToolMaterial("GOLDIAMOND", 3, 797, 10.0F, 2.0F, 16);
    public static final Item.ToolMaterial MAGNET = EnumHelper.addToolMaterial("MAGNET", 2, 452, 6.0F, 2.0F, 10);
    public static ItemSword goldiamondSword = new ItemGoldiamondSword();
    public static ItemSword magnetSword = new ItemMagnetSword();
    
    public static Item flanRPG = new ItemFlanRPG();
    public static Item flanRPGRocket = new ItemFlanRPGRocket();
    
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
        //Food
        register(cakeEgg, "cake_egg");
        
        register(faeces, "faeces");
        register(gerHeart, "ger_heart");
        register(brainDead, "brain_dead");
        //Tool
        register(goldiamondSword, "goldiamond_sword"); 
        register(magnetSword, "magnet_sword"); 
        
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
        //Food
        registerRender(cakeEgg, "cake_egg");
        
        registerRender(faeces, "faeces");
        registerRender(gerHeart, "ger_heart");
        registerRender(brainDead, "brain_dead");
        //Tool
        registerRender(goldiamondSword, "goldiamond_sword"); 
        registerRender(magnetSword, "magnet_sword"); 
        
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