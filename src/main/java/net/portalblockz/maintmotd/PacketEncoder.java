package net.portalblockz.maintmotd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket packet, ByteBuf buf) throws Exception {

        packet.write(buf);
    }
}
