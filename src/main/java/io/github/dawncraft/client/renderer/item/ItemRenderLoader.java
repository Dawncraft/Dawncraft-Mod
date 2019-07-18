package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.entity.EntityUtils;
import io.github.dawncraft.item.ItemInitializer;
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
        registerItem(ItemInitializer.bucketPetroleum);
        
        registerBlock(BlockLoader.electricCable);
        registerBlock(BlockLoader.energyGeneratorHeat);
        //        register(BlockLoader.energyGeneratorFluid);
        //        register(BlockLoader.energyGeneratorSolar);
        //        register(BlockLoader.energyGeneratorWind);
        //        register(BlockLoader.energyGeneratorNuclear);
        //        register(BlockLoader.energyGeneratorMagic);

        // Magnet
        registerItem(ItemInitializer.magnet);
        registerItem(ItemInitializer.magnetIngot);
        registerItem(ItemInitializer.magnetStick);
        registerItem(ItemInitializer.magnetBall);
        registerItem(ItemInitializer.magnetCard);
        registerItem(ItemInitializer.magnetDoor);

        registerItem(ItemInitializer.magnetAxe);
        registerItem(ItemInitializer.magnetPickaxe);
        registerItem(ItemInitializer.magnetHammer);
        registerItem(ItemInitializer.magnetSpade);
        registerItem(ItemInitializer.magnetHoe);
        registerItem(ItemInitializer.magnetSword);
        registerItem(ItemInitializer.magnetWand);
        registerItem(ItemInitializer.magnetHelmet);
        registerItem(ItemInitializer.magnetChestplate);
        registerItem(ItemInitializer.magnetLeggings);
        registerItem(ItemInitializer.magnetBoots);

        registerBlock(BlockLoader.magnetOre);
        registerBlock(BlockLoader.magnetBlock);
        registerBlock(BlockLoader.magnetRail);
        registerBlock(BlockLoader.magnetChest);

        // Machine
        registerItem(ItemInitializer.copperIngot);
        
        registerBlock(BlockLoader.copperOre);
        registerBlock(BlockLoader.copperBlock);
        registerBlock(BlockLoader.machineFurnace);

        // Computer
        registerItem(ItemInitializer.simpleCPU);
        registerItem(ItemInitializer.advancedCPU);
        registerItem(ItemInitializer.superCPU);

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
        registerItem(ItemInitializer.tumbler);
        registerItem(ItemInitializer.faeces);
        registerItem(ItemInitializer.cookedEgg);
        registerItem(ItemInitializer.honeyChicken);
        registerItem(ItemInitializer.honeyStew);
        registerItem(ItemInitializer.frogStew);
        registerItem(ItemInitializer.honey);
        registerItem(ItemInitializer.frog);

        // Magic
        registerBlock(BlockLoader.magicOre);
        registerItem(ItemInitializer.magicDust);
        registerItem(ItemInitializer.skillBook);

        // War
        registerItem(ItemInitializer.gunAK47);
        registerItem(ItemInitializer.gunBullet);
        registerItem(ItemInitializer.gunRPG);
        registerItem(ItemInitializer.gunRocket);
        registerItem(ItemInitializer.throwableTorch);

        // ColourEgg
        registerBlock(BlockLoader.superChest);
        String suffix = ItemInitializer.skull.getRegistryName() + "_";
        for (int i = 0; i < ItemSkullDawn.skullTypes.length; i++)
        {
            Class<? extends Entity> entity = ItemSkullDawn.skullTypes[i];
            registerItem(ItemInitializer.skull, i, suffix + EntityUtils.getEntityStringFromClass(entity).toLowerCase());
        }
        registerItem(ItemInitializer.gerHeart);
        registerItem(ItemInitializer.brainDead);
        registerItem(ItemInitializer.funny);

        registerItem(ItemInitializer.dj);
        registerItem(ItemInitializer.wz);

        registerItem(ItemInitializer.goldiamondSword);
        registerItem(ItemInitializer.mjolnir);
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
