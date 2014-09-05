package net.portalblock.maintmotd.io.abstracts;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public interface AbstractPacket {

    //bool = 1, byte = 1, char = 2, short = 2, int = 4, long = 8, float = 4, double = 8

    public AbstractPacket read(DataInputStream dis);

    public void write(DataOutputStream dos);

    public void handle(AbstractHandler handler);
    
}
