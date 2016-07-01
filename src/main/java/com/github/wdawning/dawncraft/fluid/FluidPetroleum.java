package com.github.wdawning.dawncraft.fluid;

import com.github.wdawning.dawncraft.dawncraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidPetroleum extends Fluid
{
    public static final ResourceLocation still = new ResourceLocation(dawncraft.MODID + ":" + "fluid/petroleum_still");
    public static final ResourceLocation flowing = new ResourceLocation(dawncraft.MODID + ":" + "fluid/petroleum_flow");
    protected static final TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();

    public FluidPetroleum()
    {
        super("petroleum", FluidPetroleum.still, FluidPetroleum.flowing);
        this.setUnlocalizedName("fluidPetroleum");
        this.setDensity(950);
        this.setViscosity(750);
        this.setLuminosity(0);
        this.setTemperature(300);
    }
}