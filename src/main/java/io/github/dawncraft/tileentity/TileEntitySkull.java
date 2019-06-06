package io.github.dawncraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.pos, 4, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager netManager, S35PacketUpdateTileEntity packet)
    {
        if (this.worldObj.isBlockLoaded(packet.getPos()))
        {
            TileEntity tileentity = this.worldObj.getTileEntity(packet.getPos());
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
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setShort("SkullType", (short) this.skullType);
        nbt.setByte("Rot", (byte) (this.skullRotation & 255));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.skullType = nbt.getShort("SkullType");
        this.skullRotation = nbt.getByte("Rot");
    }
}