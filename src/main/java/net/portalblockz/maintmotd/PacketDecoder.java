package net.portalblockz.maintmotd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class PacketDecoder extends ByteToMessageDecoder {
    private ServerInboundHandler.ConnState state;

    public PacketDecoder(){
        state = ServerInboundHandler.ConnState.HANDSHAKE;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> objects) throws Exception {
        System.out.println("State: "+state.name().toLowerCase());
        int packetSize = AbstractPacket.readVarInt(buf);
        int id = AbstractPacket.readVarInt(buf);
        AbstractPacket packet = MaintMotd.createPacket(state, id);
        if(packet == null) return;
        packet.read(buf);
        objects.add(packet);
    }

    public void setState(ServerInboundHandler.ConnState state){
        this.state = state;
        System.out.println("Set state to: "+state.name().toLowerCase());
    }
}
