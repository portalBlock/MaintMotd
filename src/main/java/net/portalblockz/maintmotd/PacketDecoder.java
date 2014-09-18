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
        int packetsRead = 0;
        while(buf.readableBytes() != 0) {
            //int packetSize = AbstractPacket.readVarInt(buf);
            int id = AbstractPacket.readVarInt(buf);
            AbstractPacket packet = MaintMotd.createPacket(state, id);
            if(packet == null){
                buf.skipBytes(buf.readableBytes());
                return;
            }
            packet.read(buf);
            objects.add(packet);
            packetsRead++;
        }
    }

    public void setState(ServerInboundHandler.ConnState state){
        this.state = state;
    }
}
