package net.portalblock.maintmotd.io.packets;

import net.portalblock.maintmotd.Utils;
import net.portalblock.maintmotd.io.abstracts.AbstractHandler;
import net.portalblock.maintmotd.io.abstracts.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class Handshake implements AbstractPacket {

    private String hostName;
    private int protocolVersion, nextState, port;

    @Override
    public AbstractPacket read(DataInputStream dis) {
        protocolVersion = Utils.readVarInt(dis);
        try{
            hostName = Utils.readString(dis);
            port = dis.readUnsignedShort();
        }catch (Exception e){

        }
        nextState = Utils.readVarInt(dis);
        System.out.println(hostName+":"+port+"\n"+protocolVersion+"\n"+nextState);
        return this;
    }

    @Override
    public void write(DataOutputStream dos) {

    }

    @Override
    public void handle(AbstractHandler handler) {
        handler.handle(this);
    }
}
