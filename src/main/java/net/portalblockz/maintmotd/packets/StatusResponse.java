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
public class StatusResponse extends AbstractPacket {

    private String jsonResponse;

    @Override
    public void write(ByteBuf buf) {
        writeVarInt(0x00, buf);
        writeString(jsonResponse, buf);
    }

    @Override
    public void read(ByteBuf buf) {
        jsonResponse = readString(buf);
    }

    @Override
    public void handle(ServerInboundHandler handler) {
        handler.handle(this);
    }
}
