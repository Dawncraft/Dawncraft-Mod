package io.github.dawncraft.container;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.gui.container.GuiEnergyGenerator;
import io.github.dawncraft.client.gui.container.GuiMachineFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiLoader implements IGuiHandler
{
    // Energy
    public static final int GUI_HEAT_GENERATOR = 1;
    // Machine
    public static final int GUI_MACHINE_FURNACE = 2;
    // Computer
    public static final int GUI_COMPUTER_CASE = 3;
    // Furniture

    // Cuisine
    
    // War

    // Magic
    public static final int GUI_SKILL_INVENTORY = 100;
    
    public static void initGuiHandler()
    {
        registerGuiHandler(new GuiLoader());
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case GUI_HEAT_GENERATOR:
                return new ContainerEnergyGenerator(player, player.worldObj.getTileEntity(new BlockPos(x, y, z)));
            case GUI_MACHINE_FURNACE:
                return new ContainerMachineFurnace(player, player.worldObj.getTileEntity(new BlockPos(x, y, z)));
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
                return new GuiEnergyGenerator(player, player.worldObj.getTileEntity(new BlockPos(x, y, z)));
            case GUI_MACHINE_FURNACE:
                return new GuiMachineFurnace(player, player.worldObj.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    private static void registerGuiHandler(IGuiHandler handler)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Dawncraft.instance, handler);
    }
}
