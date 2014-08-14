package net.portalblock.maintmotd.io;

import net.portalblock.maintmotd.Utils;
import net.portalblock.maintmotd.io.abstracts.AbstractHandler;
import net.portalblock.maintmotd.io.packets.Handshake;
import net.portalblock.maintmotd.io.packets.StatusRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class PacketHandler extends AbstractHandler {

    private DataInputStream dis;
    private DataOutputStream dos;

    public PacketHandler(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void handle(Handshake h) {
        System.out.println("Handshook");
    }

    @Override
    public void handle(StatusRequest sr) {
        Utils.writeVarInt(0x00, dos);
        String json = "{\"version\": {\"name\": \"MaintMotd\",\"protocol\": 0},\"players\": {\"max\": 100,\"online\": 5,\"sample\":[{\"name\":\"Back up soon!\", \"id\":\"\"}]},\"description\": {\"text\":\"Hello world\"},\"favicon\": \"data:image/png;base64,<data>\"}";
        Utils.writeString(json, dos);
    }
}
