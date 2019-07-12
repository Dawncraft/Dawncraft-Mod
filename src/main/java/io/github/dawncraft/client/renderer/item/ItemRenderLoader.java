package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.entity.EntityUtils;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.item.ItemSkullDawn;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Register items' inventory model.(Include ItemBlock)
 *
 * @author QingChenW
 */
public class ItemRenderLoader
{
    public static void initItemRender()
    {
        // Energy
        registerItem(ItemLoader.bucketPetroleum);
        
        registerBlock(BlockLoader.electricCable);
        registerBlock(BlockLoader.energyGeneratorHeat);
        //        register(BlockLoader.energyGeneratorFluid);
        //        register(BlockLoader.energyGeneratorSolar);
        //        register(BlockLoader.energyGeneratorWind);
        //        register(BlockLoader.energyGeneratorNuclear);
        //        register(BlockLoader.energyGeneratorMagic);

        // Magnet
        registerItem(ItemLoader.magnet);
        registerItem(ItemLoader.magnetIngot);
        registerItem(ItemLoader.magnetStick);
        registerItem(ItemLoader.magnetBall);
        registerItem(ItemLoader.magnetCard);
        registerItem(ItemLoader.magnetDoor);

        registerItem(ItemLoader.magnetAxe);
        registerItem(ItemLoader.magnetPickaxe);
        registerItem(ItemLoader.magnetHammer);
        registerItem(ItemLoader.magnetSpade);
        registerItem(ItemLoader.magnetHoe);
        registerItem(ItemLoader.magnetSword);
        registerItem(ItemLoader.magnetWand);
        registerItem(ItemLoader.magnetHelmet);
        registerItem(ItemLoader.magnetChestplate);
        registerItem(ItemLoader.magnetLeggings);
        registerItem(ItemLoader.magnetBoots);

        registerBlock(BlockLoader.magnetOre);
        registerBlock(BlockLoader.magnetBlock);
        registerBlock(BlockLoader.magnetRail);
        registerBlock(BlockLoader.magnetChest);

        // Machine
        registerItem(ItemLoader.copperIngot);
        
        registerBlock(BlockLoader.copperOre);
        registerBlock(BlockLoader.copperBlock);
        registerBlock(BlockLoader.machineFurnace);

        // Computer
        registerItem(ItemLoader.simpleCPU);
        registerItem(ItemLoader.advancedCPU);
        registerItem(ItemLoader.superCPU);

        registerBlock(BlockLoader.simpleComputer);
        registerBlock(BlockLoader.advancedComputer);
        registerBlock(BlockLoader.superComputer);

        // Science

        // Furniture
        registerBlock(BlockLoader.woodTable);
        registerBlock(BlockLoader.stoneTable);
        registerBlock(BlockLoader.woodChair);
        registerBlock(BlockLoader.stoneChair);
        registerBlock(BlockLoader.alarmClock);

        // Cuisine
        registerItem(ItemLoader.tumbler);
        registerItem(ItemLoader.faeces);
        registerItem(ItemLoader.cookedEgg);
        registerItem(ItemLoader.honeyChicken);
        registerItem(ItemLoader.honeyStew);
        registerItem(ItemLoader.frogStew);
        registerItem(ItemLoader.honey);
        registerItem(ItemLoader.frog);

        // Magic
        registerBlock(BlockLoader.magicOre);
        registerItem(ItemLoader.magicDust);
        registerItem(ItemLoader.skillBook);

        // War
        registerItem(ItemLoader.gunAK47);
        registerItem(ItemLoader.gunBullet);
        registerItem(ItemLoader.gunRPG);
        registerItem(ItemLoader.gunRocket);
        registerItem(ItemLoader.throwableTorch);

        // ColourEgg
        registerBlock(BlockLoader.superChest);
        String suffix = ItemLoader.skull.getRegistryName() + "_";
        for (int i = 0; i < ItemSkullDawn.skullTypes.length; i++)
        {
            Class<? extends Entity> entity = ItemSkullDawn.skullTypes[i];
            registerItem(ItemLoader.skull, i, suffix + EntityUtils.getEntityStringFromClass(entity).toLowerCase());
        }
        registerItem(ItemLoader.gerHeart);
        registerItem(ItemLoader.brainDead);
        registerItem(ItemLoader.funny);

        registerItem(ItemLoader.dj);
        registerItem(ItemLoader.wz);

        registerItem(ItemLoader.goldiamondSword);
        registerItem(ItemLoader.mjolnir);
    }

    /**
     * Register a item's inventory model and its variants.
     *
     * @param item item's string id
     */
    private static void registerItem(Item item)
    {
        registerItem(item, 0, item.getRegistryName());
    }

    /**
     * Register a block's inventory model and its variants.
     *
     * @param block block's string id
     */
    private static void registerBlock(Block block)
    {
        registerBlock(block, 0, block.getRegistryName());
    }
    
    /**
     * Register a item's inventory model with meta and name and its variants.
     *
     * @param item item to register
     * @param meta item's meta
     * @param name item's model name
     */
    private static void registerItem(Item item, int meta, String name)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
    }

    /**
     * Register a block's inventory model with meta and name and its variants.
     *
     * @param block block to register
     * @param meta item's meta
     * @param name item's model name
     */
    private static void registerBlock(Block block, int meta, String name)
    {
        registerItem(Item.getItemFromBlock(block), meta, name);
    }
}
