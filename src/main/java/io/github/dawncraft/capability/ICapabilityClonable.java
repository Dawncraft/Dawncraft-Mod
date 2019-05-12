package io.github.dawncraft.capability;

public interface ICapabilityClonable<T>
{
    public T cloneCapability(T old, boolean wasDeath);
}
