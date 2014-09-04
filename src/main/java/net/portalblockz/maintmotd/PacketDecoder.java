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
        int id = AbstractPacket.readVarInt(buf);
        int packetSize = AbstractPacket.readVarInt(buf);
        AbstractPacket packet = MaintMotd.createPacket(state, id);
        if(packet == null) return;
        packet.read(buf);
        objects.add(packet);
    }

    public void setState(ServerInboundHandler.ConnState state){
        this.state = state;
    }
}
