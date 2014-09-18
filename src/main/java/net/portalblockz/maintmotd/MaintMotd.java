/*
 * Copyright (c) 2012, md_5. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * You may not use the software for commercial software hosting services without
 * written permission from the author.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

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
                        p.addLast("frame-prepender", new FramePrepender());
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
