package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.entity.EntityUtils;
import io.github.dawncraft.item.ItemInit;
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
        registerItem(ItemInit.bucketPetroleum);
        
        registerBlock(BlockInit.electricCable);
        registerBlock(BlockInit.energyGeneratorHeat);
        //        register(BlockLoader.energyGeneratorFluid);
        //        register(BlockLoader.energyGeneratorSolar);
        //        register(BlockLoader.energyGeneratorWind);
        //        register(BlockLoader.energyGeneratorNuclear);
        //        register(BlockLoader.energyGeneratorMagic);

        // Magnet
        registerItem(ItemInit.magnet);
        registerItem(ItemInit.magnetIngot);
        registerItem(ItemInit.magnetStick);
        registerItem(ItemInit.magnetBall);
        registerItem(ItemInit.magnetCard);
        registerItem(ItemInit.magnetDoor);

        registerItem(ItemInit.magnetAxe);
        registerItem(ItemInit.magnetPickaxe);
        registerItem(ItemInit.magnetHammer);
        registerItem(ItemInit.magnetSpade);
        registerItem(ItemInit.magnetHoe);
        registerItem(ItemInit.magnetSword);
        registerItem(ItemInit.magnetWand);
        registerItem(ItemInit.magnetHelmet);
        registerItem(ItemInit.magnetChestplate);
        registerItem(ItemInit.magnetLeggings);
        registerItem(ItemInit.magnetBoots);

        registerBlock(BlockInit.magnetOre);
        registerBlock(BlockInit.magnetBlock);
        registerBlock(BlockInit.magnetRail);
        registerBlock(BlockInit.magnetChest);

        // Machine
        registerItem(ItemInit.copperIngot);
        
        registerBlock(BlockInit.copperOre);
        registerBlock(BlockInit.copperBlock);
        registerBlock(BlockInit.machineFurnace);

        // Computer
        registerItem(ItemInit.simpleCPU);
        registerItem(ItemInit.advancedCPU);
        registerItem(ItemInit.superCPU);

        registerBlock(BlockInit.simpleComputer);
        registerBlock(BlockInit.advancedComputer);
        registerBlock(BlockInit.superComputer);

        // Science

        // Furniture
        registerBlock(BlockInit.woodTable);
        registerBlock(BlockInit.stoneTable);
        registerBlock(BlockInit.woodChair);
        registerBlock(BlockInit.stoneChair);
        registerBlock(BlockInit.alarmClock);

        // Cuisine
        registerItem(ItemInit.tumbler);
        registerItem(ItemInit.faeces);
        registerItem(ItemInit.cookedEgg);
        registerItem(ItemInit.honeyChicken);
        registerItem(ItemInit.honeyStew);
        registerItem(ItemInit.frogStew);
        registerItem(ItemInit.honey);
        registerItem(ItemInit.frog);

        // Magic
        registerBlock(BlockInit.magicOre);
        registerItem(ItemInit.magicDust);
        registerItem(ItemInit.skillBook);

        // War
        registerItem(ItemInit.gunAK47);
        registerItem(ItemInit.gunBullet);
        registerItem(ItemInit.gunRPG);
        registerItem(ItemInit.gunRocket);
        registerItem(ItemInit.throwableTorch);

        // ColourEgg
        registerBlock(BlockInit.superChest);
        String suffix = ItemInit.skull.getRegistryName() + "_";
        for (int i = 0; i < ItemSkullDawn.skullTypes.length; i++)
        {
            Class<? extends Entity> entity = ItemSkullDawn.skullTypes[i];
            registerItem(ItemInit.skull, i, suffix + EntityUtils.getEntityStringFromClass(entity).toLowerCase());
        }
        registerItem(ItemInit.gerHeart);
        registerItem(ItemInit.brainDead);
        registerItem(ItemInit.funny);

        registerItem(ItemInit.dj);
        registerItem(ItemInit.wz);

        registerItem(ItemInit.goldiamondSword);
        registerItem(ItemInit.mjolnir);
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
