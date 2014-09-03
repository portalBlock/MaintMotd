package net.portalblockz.maintmotd;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.portalblockz.maintmotd.packets.Handshake;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class ServerInboundHandler extends SimpleChannelInboundHandler<AbstractPacket> {
    private ConnState state;
    private PacketDecoder decoder;
    private Channel channel;

    public ServerInboundHandler(PacketDecoder decoder){
        state = ConnState.HANDSHAKE;
        this.decoder = decoder;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractPacket abstractPacket) throws Exception {
        abstractPacket.handle(this);
    }

    public void handle(Handshake handshake){
        state = ConnState.STATUS;
        decoder.setState(state);
    }

    public enum ConnState{
        HANDSHAKE,
        STATUS,
        PING,
        GAME
    }
}
