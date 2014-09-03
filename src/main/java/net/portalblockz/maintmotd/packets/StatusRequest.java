package net.portalblockz.maintmotd.packets;

import io.netty.buffer.ByteBuf;
import net.portalblockz.maintmotd.AbstractPacket;
import net.portalblockz.maintmotd.ServerInboundHandler;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class StatusRequest extends AbstractPacket {

    @Override
    public void write(ByteBuf buf) {
        writeVarInt(0x00, buf);
    }

    @Override
    public void read(ByteBuf buf) {

    }

    @Override
    public void handle(ServerInboundHandler handler) {
        handler.handle(this);
    }
}
