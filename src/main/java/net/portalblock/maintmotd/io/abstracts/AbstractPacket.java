package net.portalblock.maintmotd.io.abstracts;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public interface AbstractPacket {

    public AbstractPacket read(DataInputStream dis);

    public void write(DataOutputStream dos);

    public void handle(AbstractHandler handler);
    
}
