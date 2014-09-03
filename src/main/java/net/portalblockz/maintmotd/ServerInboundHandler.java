package net.portalblockz.maintmotd;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.portalblockz.maintmotd.packets.Handshake;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class ServerInboundHandler extends SimpleChannelInboundHandler<AbstractPacket> {
    private ConnState state;
    private PacketDecoder decoder;

    public ServerInboundHandler(PacketDecoder decoder){
        state = ConnState.HANDSHAKE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractPacket abstractPacket) throws Exception {
        abstractPacket.handle(this);
    }

    public void handle(Handshake handshake){
        state = ConnState.STATUS;
    }

    public enum ConnState{
        HANDSHAKE,
        STATUS,
        PING,
        GAME
    }
}