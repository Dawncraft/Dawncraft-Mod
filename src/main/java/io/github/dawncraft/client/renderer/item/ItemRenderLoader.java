package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register items' inventory model.(Include ItemBlock)
 *
 * @author QingChenW
 */
public class ItemRenderLoader
{
    public ItemRenderLoader(FMLPreInitializationEvent event)
    {
        // Energy
        registerRender(ItemLoader.bucketPetroleum);
        
        registerRender(BlockLoader.electricCable);
        registerRender(BlockLoader.energyGeneratorHeat);
        // registerRender(BlockLoader.energyGeneratorFluid);
        // registerRender(BlockLoader.energyGeneratorSolar);
        // registerRender(BlockLoader.energyGeneratorWind);
        // registerRender(BlockLoader.energyGeneratorNuclear);
        // registerRender(BlockLoader.energyGeneratorMagic);

        // Magnet
        registerRender(ItemLoader.magnet);
        registerRender(ItemLoader.magnetIngot);
        registerRender(ItemLoader.magnetStick);
        registerRender(ItemLoader.magnetBall);
        registerRender(ItemLoader.magnetCard);
        registerRender(ItemLoader.magnetDoor);

        registerRender(ItemLoader.magnetAxe);
        registerRender(ItemLoader.magnetPickaxe);
        registerRender(ItemLoader.magnetHammer);
        registerRender(ItemLoader.magnetSpade);
        registerRender(ItemLoader.magnetHoe);
        registerRender(ItemLoader.magnetSword);
        registerRender(ItemLoader.magnetWand);
        registerRender(ItemLoader.magnetHelmet);
        registerRender(ItemLoader.magnetChestplate);
        registerRender(ItemLoader.magnetLeggings);
        registerRender(ItemLoader.magnetBoots);

        registerRender(BlockLoader.magnetOre);
        registerRender(BlockLoader.magnetBlock);
        registerRender(BlockLoader.magnetRail);

        // Machine
        registerRender(ItemLoader.copperIngot);
        
        registerRender(BlockLoader.copperOre);
        registerRender(BlockLoader.machineFurnace);

        // Computer
        registerRender(ItemLoader.simpleCPU);
        registerRender(ItemLoader.advancedCPU);
        registerRender(ItemLoader.superCPU);

        registerRender(BlockLoader.simpleComputer);
        registerRender(BlockLoader.advancedComputer);
        registerRender(BlockLoader.superComputer);

        // Materials

        // Furniture
        registerRender(BlockLoader.woodTable);
        registerRender(BlockLoader.stoneTable);
        registerRender(BlockLoader.superChest);

        // Food
        registerRender(ItemLoader.faeces);
        registerRender(ItemLoader.cakeEgg);

        // Magic
        registerRender(ItemLoader.magicDust);
        registerRender(ItemLoader.magicBook);
        registerRender(ItemLoader.metalEssence);
        registerRender(ItemLoader.woodEssence);
        registerRender(ItemLoader.waterEssence);
        registerRender(ItemLoader.fireEssence);
        registerRender(ItemLoader.dirtEssence);

        registerRender(BlockLoader.magicOre);

        // Guns
        registerRender(ItemLoader.gunAK47);
        registerRender(ItemLoader.gunBullet);
        registerRender(ItemLoader.gunRPG);
        registerRender(ItemLoader.gunRocket);

        // ColourEgg
        registerRender(ItemLoader.skull, 0, ItemLoader.skull.getRegistryName() + "_savage");
        registerRender(ItemLoader.skull, 1, ItemLoader.skull.getRegistryName() + "_barbarianking");
        registerRender(ItemLoader.skull, 2, ItemLoader.skull.getRegistryName() + "_gerking");
        registerRender(ItemLoader.gerHeart);
        registerRender(ItemLoader.brainDead);
        registerRender(ItemLoader.funny);

        registerRender(ItemLoader.chinese);
        registerRender(ItemLoader.dj);
        registerRender(ItemLoader.wz1);
        registerRender(ItemLoader.wz2);

        registerRender(ItemLoader.goldiamondSword);
        registerRender(ItemLoader.mjolnir);
    }

    /**
     * Register a item's inventory model and its variants.
     *
     * @param item item's string id
     */
    private static void registerRender(Item item)
    {
        registerRender(item, 0, item.getRegistryName());
    }

    /**
     * Register a block's inventory model and its variants.
     *
     * @param block block's string id
     */
    private static void registerRender(Block block)
    {
        registerRender(block, 0, block.getRegistryName());
    }
    
    /**
     * Register a item's inventory model with meta and name and its variants.
     *
     * @param item item to register
     * @param meta item's meta
     * @param name item's model suffix
     */
    private static void registerRender(Item item, int meta, String name)
    {
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

    /**
     * Register a block's inventory model with meta and name and its variants.
     *
     * @param block block to register
     * @param meta item's meta
     * @param name item's model suffix
     */
    private static void registerRender(Block block, int meta, String name)
    {
        Item item = Item.getItemFromBlock(block);
        registerRender(item, meta, name);
    }
}
