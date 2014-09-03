package net.portalblockz.maintmotd.packets;

import io.netty.buffer.ByteBuf;
import net.portalblockz.maintmotd.AbstractPacket;
import net.portalblockz.maintmotd.ServerInboundHandler;

import java.io.DataInputStream;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class Handshake extends AbstractPacket {

    private String hostName;
    private int protocolVersion, nextState, port;

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void read(ByteBuf buf) {
        protocolVersion = readVarInt(buf);
        try{
            hostName = readString(buf);
            port = buf.readUnsignedShort();
        }catch (Exception e){

        }
        nextState = readVarInt(buf);
        System.out.println(hostName+":"+port+"\n"+protocolVersion+"\n"+nextState);
    }

    @Override
    public void handle(ServerInboundHandler handler) {
        handler.handle(this);
    }
}
