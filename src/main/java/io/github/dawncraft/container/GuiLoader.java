package io.github.dawncraft.container;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.client.gui.inventory.GuiEnergyHeatGen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiLoader implements IGuiHandler
{
    // Energy
    public static final int GUI_HEAT_GENERATOR = 1;
    // Machine
    public static final int GUI_ELECTRICITY_FURNACE = 33;
    // Computer
    
    // Furniture
    
    // Food
    
    // Magic
    
    // Flan
    
    
    public GuiLoader(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(dawncraft.instance, this);
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case GUI_HEAT_GENERATOR:
            return new ContainerEnergyHeatGen(player, player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case GUI_HEAT_GENERATOR:
            return new GuiEnergyHeatGen(player, player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
