package net.portalblock.maintmotd.io.packets;

import net.portalblock.maintmotd.io.abstracts.AbstractHandler;
import net.portalblock.maintmotd.io.abstracts.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class PingPacket implements AbstractPacket {

    private long time;

    @Override
    public AbstractPacket read(DataInputStream dis) {
        try{
            time = dis.readLong();
        }catch (Exception e){

        }
        return this;
    }

    @Override
    public void write(DataOutputStream dos) {
        try{
            dos.writeLong(time);
            dos.flush();
        }catch (Exception e){

        }
    }

    @Override
    public void handle(AbstractHandler handler) {
        handler.handle(this);
    }
}
