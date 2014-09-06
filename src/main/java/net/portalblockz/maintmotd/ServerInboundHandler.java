package net.portalblockz.maintmotd;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.portalblockz.maintmotd.packets.Handshake;
import net.portalblockz.maintmotd.packets.Ping;
import net.portalblockz.maintmotd.packets.StatusRequest;
import net.portalblockz.maintmotd.packets.StatusResponse;

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
        decoder.setState(ConnState.STATUS);
        state = ConnState.STATUS;
        System.out.println("Handling handshake");
        System.out.println("****STATE SET TO STATUS***");
    }

    public void handle(StatusRequest request){
        state = ConnState.PING;
        channel.writeAndFlush(new StatusResponse("{\"version\": {\"name\": \"MaintMotd\",\"protocol\": 0},\"players\": {\"max\": 100,\"online\": 5,\"sample\":[{\"name\":\"Back up soon!\", \"id\":\"\"}]},\"description\": {\"text\":\"Hello world\"}}"));
        decoder.setState(ConnState.PING);
    }

    public void handle(StatusResponse response){

    }

    public void handle(Ping ping){
        channel.writeAndFlush(ping);
        channel.close();
    }

    public enum ConnState{
        HANDSHAKE,
        STATUS,
        PING,
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
