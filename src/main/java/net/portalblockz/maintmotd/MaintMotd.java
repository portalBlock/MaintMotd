package net.portalblockz.maintmotd;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.portalblockz.maintmotd.packets.Handshake;
import net.portalblockz.maintmotd.packets.Ping;
import net.portalblockz.maintmotd.packets.StatusRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class MaintMotd {
    private static Map<Integer, Class<? extends AbstractPacket>> handshakePackets = new HashMap<Integer, Class<? extends AbstractPacket>>();
    private static Map<Integer, Class<? extends AbstractPacket>> statusPackets = new HashMap<Integer, Class<? extends AbstractPacket>>();

    public static void main(String[] args){
        handshakePackets.put(0x00, Handshake.class);
        statusPackets.put(0x00, StatusRequest.class);
        statusPackets.put(0x01, Ping.class);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        PacketDecoder decoder = new PacketDecoder();
                        p.addLast("timeout", new ReadTimeoutHandler(20));
                        p.addLast("packet-encoder", new PacketEncoder());
                        p.addLast("frame-decoder", new VarInt21FrameDecoder());
                        p.addLast("packet-decoder", decoder);
                        p.addLast("inbound-boss", new ServerInboundHandler(decoder));
                    }
                })
                .group(new NioEventLoopGroup())
                .bind(25565)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("Connection: "+channelFuture.isSuccess());
                    }
                });
    }

    public static AbstractPacket createPacket(ServerInboundHandler.ConnState state, int id){
        try{
            switch (state){
                case HANDSHAKE: return getPacketFromMap(handshakePackets, id).newInstance();
                case STATUS: return getPacketFromMap(statusPackets, id).newInstance();
                default: return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    private static Class<? extends AbstractPacket> getPacketFromMap(Map<Integer, Class<? extends AbstractPacket>> map, int id){
        try{
            return map.get(id);
        }catch (Exception e){
            return null;
        }
    }

}
