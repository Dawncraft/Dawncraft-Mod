package io.github.dawncraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** TileEntitySkull 头颅实体类
 * <br>种类请在ItemSkullBase中定义</br>
 * 另外由于原版的skull包括玩家头，因此本skull去除玩家头
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
    public boolean useByRenderer()
    {
        return this.useByItemStackRenderer;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("SkullType", this.skullType);
        compound.setByte("Rot", (byte)(this.skullRotation & 255));
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.skullType = compound.getInteger("SkullType");
        this.skullRotation = compound.getByte("Rot");
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.pos, 4, nbttagcompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager netManager, S35PacketUpdateTileEntity packetIn)
    {
        if (this.worldObj.isBlockLoaded(packetIn.getPos()))
        {
            TileEntity tileentity = this.worldObj.getTileEntity(packetIn.getPos());
            int i = packetIn.getTileEntityType();
            
            if (i == 4 && tileentity instanceof TileEntitySkull)
            {
                tileentity.readFromNBT(packetIn.getNbtCompound());
            }
            else
            {
                tileentity.onDataPacket(netManager, packetIn);
            }
        }
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
}