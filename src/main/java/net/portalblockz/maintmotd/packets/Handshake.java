package net.portalblockz.maintmotd.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.portalblockz.maintmotd.AbstractPacket;
import net.portalblockz.maintmotd.ServerInboundHandler;


/**
 * Created by portalBlock on 9/2/2014.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Handshake extends AbstractPacket {

    private String hostName;
    private int protocolVersion, nextState, port;

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void read(ByteBuf buf) {
        System.out.println("Reading PV");
        protocolVersion = readVarInt(buf);
        System.out.println("Reading HN");
        hostName = readString(buf);
        System.out.println("Reading prt");
        port = buf.readUnsignedShort();
        System.out.println("Reading nextState");
        nextState = readVarInt(buf);
        System.out.println(hostName+":"+port+"\n"+protocolVersion+"\n"+nextState);
    }

    @Override
    public void handle(ServerInboundHandler handler) {
        handler.handle(this);
    }
}
