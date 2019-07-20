package io.github.dawncraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The tile entity of dawncraft's skull block.
 *
 * @author QingChenW
 */
public class TileEntitySkull extends TileEntity
{
    private boolean useByItemStackRenderer = false;

    private int skullType;
    private int skullRotation;

    public TileEntitySkull() {}

    @SideOnly(Side.CLIENT)
    public TileEntitySkull(int skullType)
    {
        this.useByItemStackRenderer = true;
        this.skullType = skullType;
    }

    @SideOnly(Side.CLIENT)
    public boolean usedByItemStackRenderer()
    {
        return this.useByItemStackRenderer;
    }

    public void setSkullType(int type)
    {
        this.skullType = type;
    }

    public int getSkullType()
    {
        return this.skullType;
    }

    public void setSkullRotation(int rotation)
    {
        this.skullRotation = rotation;
    }

    @SideOnly(Side.CLIENT)
    public int getSkullRotation()
    {
        return this.skullRotation;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new SPacketUpdateTileEntity(this.pos, 4, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager netManager, SPacketUpdateTileEntity packet)
    {
        if (this.world.isBlockLoaded(packet.getPos()))
        {
            TileEntity tileentity = this.world.getTileEntity(packet.getPos());
            int i = packet.getTileEntityType();

            if (i == 4 && tileentity instanceof TileEntitySkull)
            {
                tileentity.readFromNBT(packet.getNbtCompound());
            }
            else
            {
                tileentity.onDataPacket(netManager, packet);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("SkullType", (short) this.skullType);
        compound.setByte("Rot", (byte) (this.skullRotation & 255));
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.skullType = compound.getShort("SkullType");
        this.skullRotation = compound.getByte("Rot");
    }
}