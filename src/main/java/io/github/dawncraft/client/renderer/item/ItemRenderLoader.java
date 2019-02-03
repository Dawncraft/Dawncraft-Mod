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
 * <br>这是啥Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register()</br>
 *
 * @author QingChenW
 */
public class ItemRenderLoader
{
    public ItemRenderLoader(FMLPreInitializationEvent event)
    {
        // Energy
        register(ItemLoader.bucketPetroleum);
        
        register(BlockLoader.electricCable);
        register(BlockLoader.energyGeneratorHeat);
        //        register(BlockLoader.energyGeneratorFluid);
        //        register(BlockLoader.energyGeneratorSolar);
        //        register(BlockLoader.energyGeneratorWind);
        //        register(BlockLoader.energyGeneratorNuclear);
        //        register(BlockLoader.energyGeneratorMagic);

        // Magnet
        register(ItemLoader.magnet);
        register(ItemLoader.magnetIngot);
        register(ItemLoader.magnetStick);
        register(ItemLoader.magnetBall);
        register(ItemLoader.magnetCard);
        register(ItemLoader.magnetDoor);

        register(ItemLoader.magnetAxe);
        register(ItemLoader.magnetPickaxe);
        register(ItemLoader.magnetHammer);
        register(ItemLoader.magnetSpade);
        register(ItemLoader.magnetHoe);
        register(ItemLoader.magnetSword);
        register(ItemLoader.magnetWand);
        register(ItemLoader.magnetHelmet);
        register(ItemLoader.magnetChestplate);
        register(ItemLoader.magnetLeggings);
        register(ItemLoader.magnetBoots);

        register(BlockLoader.magnetOre);
        register(BlockLoader.magnetBlock);
        register(BlockLoader.magnetRail);
        register(BlockLoader.magnetChest);

        // Machine
        register(ItemLoader.copperIngot);
        
        register(BlockLoader.copperOre);
        register(BlockLoader.copperBlock);
        register(BlockLoader.machineFurnace);

        // Computer
        register(ItemLoader.simpleCPU);
        register(ItemLoader.advancedCPU);
        register(ItemLoader.superCPU);

        register(BlockLoader.simpleComputer);
        register(BlockLoader.advancedComputer);
        register(BlockLoader.superComputer);

        // Materials

        // Furniture
        register(BlockLoader.woodTable);
        register(BlockLoader.stoneTable);
        register(BlockLoader.woodChair);
        register(BlockLoader.stoneChair);
        register(BlockLoader.superChest);

        // Food
        register(ItemLoader.bottle);
        register(ItemLoader.faeces);
        register(ItemLoader.cookedEgg);
        register(ItemLoader.honeyChicken);
        register(ItemLoader.honeyStew);
        register(ItemLoader.frogStew);
        register(ItemLoader.honey);
        register(ItemLoader.frog);

        // Magic
        register(ItemLoader.magicDust);
        register(ItemLoader.magicBook);
        register(ItemLoader.metalEssence);
        register(ItemLoader.woodEssence);
        register(ItemLoader.waterEssence);
        register(ItemLoader.fireEssence);
        register(ItemLoader.dirtEssence);

        register(BlockLoader.magicOre);

        // Guns
        register(ItemLoader.gunAK47);
        register(ItemLoader.gunBullet);
        register(ItemLoader.gunRPG);
        register(ItemLoader.gunRocket);

        // ColourEgg
        register(ItemLoader.skull, 0, ItemLoader.skull.getRegistryName() + "_savage");
        register(ItemLoader.skull, 1, ItemLoader.skull.getRegistryName() + "_barbarianking");
        register(ItemLoader.skull, 2, ItemLoader.skull.getRegistryName() + "_gerking");
        register(ItemLoader.gerHeart);
        register(ItemLoader.brainDead);
        register(ItemLoader.funny);

        register(ItemLoader.dj);
        register(ItemLoader.wz);

        register(ItemLoader.goldiamondSword);
        register(ItemLoader.mjolnir);
    }

    /**
     * Register a item's inventory model and its variants.
     *
     * @param item item's string id
     */
    private static void register(Item item)
    {
        register(item, 0, item.getRegistryName());
    }

    /**
     * Register a block's inventory model and its variants.
     *
     * @param block block's string id
     */
    private static void register(Block block)
    {
        register(block, 0, block.getRegistryName());
    }
    
    /**
     * Register a item's inventory model with meta and name and its variants.
     *
     * @param item item to register
     * @param meta item's meta
     * @param name item's model suffix
     */
    private static void register(Item item, int meta, String name)
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
    private static void register(Block block, int meta, String name)
    {
        Item item = Item.getItemFromBlock(block);
        register(item, meta, name);
    }
}
