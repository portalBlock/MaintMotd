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
public class Ping extends AbstractPacket {
    private long time;

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void read(ByteBuf buf) {

    }

    @Override
    public void handle(ServerInboundHandler handler) {

    }
}
