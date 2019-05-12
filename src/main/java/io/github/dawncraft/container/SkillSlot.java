package io.github.dawncraft.container;

import io.github.dawncraft.skill.SkillStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkillSlot
{
    /** The index of the slot in the inventory. */
    private final int slotIndex;
    /** The inventory we want to extract a slot from. */
    public final ISkillInventory inventory;
    /** the id of the slot(also the index in the inventory arraylist) */
    public int slotNumber;
    /** display position of the inventory slot on the screen x axis */
    public int xDisplayPosition;
    /** display position of the inventory slot on the screen y axis */
    public int yDisplayPosition;

    public SkillSlot(ISkillInventory inventory, int index, int xPosition, int yPosition)
    {
        this.inventory = inventory;
        this.slotIndex = index;
        this.xDisplayPosition = xPosition;
        this.yDisplayPosition = yPosition;
    }
    
    public int getSlotIndex()
    {
        return this.slotIndex;
    }

    public boolean hasStack()
    {
        return this.getStack() != null;
    }
    
    public SkillStack getStack()
    {
        return this.inventory.getStackInSlot(this.slotIndex);
    }

    public boolean isHere(ISkillInventory inventory, int slot)
    {
        return inventory == this.inventory && slot == this.slotIndex;
    }

    public boolean isSkillValid(SkillStack stack)
    {
        return true;
    }
    
    public void putStack(SkillStack stack)
    {
        this.inventory.setInventorySlot(this.slotIndex, stack);
        this.onSlotChanged();
    }
    
    public SkillStack removeStack()
    {
        return this.inventory.removeStackFromSlot(this.slotIndex);
    }
    
    public boolean canTakeStack(EntityPlayer player)
    {
        return true;
    }

    public void onPickupFromSlot(EntityPlayer player, SkillStack stack)
    {
        this.onSlotChanged();
    }
    
    public void onSlotChanged()
    {
        this.inventory.markDirty();
    }

    public void onLearning(SkillStack stack)
    {
    }

    @SideOnly(Side.CLIENT)
    public boolean canBeHovered()
    {
        return true;
    }
    
    protected String backgroundName = null;
    protected ResourceLocation backgroundLocation = null;
    protected Object backgroundMap;
    
    @SideOnly(Side.CLIENT)
    public ResourceLocation getBackgroundLocation()
    {
        return this.backgroundLocation == null ? TextureMap.locationBlocksTexture : this.backgroundLocation;
    }

    public void setBackgroundLocation(ResourceLocation texture)
    {
        this.backgroundLocation = texture;
    }

    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return this.backgroundName;
    }

    public void setBackgroundName(String name)
    {
        this.backgroundName = name;
    }

    @SideOnly(Side.CLIENT)
    protected TextureMap getBackgroundMap()
    {
        if (this.backgroundMap == null) this.backgroundMap = Minecraft.getMinecraft().getTextureMapBlocks();
        return (TextureMap) this.backgroundMap;
    }
    
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getBackgroundSprite()
    {
        String name = this.getSlotTexture();
        return name == null ? null : this.getBackgroundMap().getAtlasSprite(name);
    }
}
