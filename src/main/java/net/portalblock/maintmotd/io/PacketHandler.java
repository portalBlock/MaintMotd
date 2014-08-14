package net.portalblock.maintmotd.io;

import net.portalblock.maintmotd.Utils;
import net.portalblock.maintmotd.io.abstracts.AbstractHandler;
import net.portalblock.maintmotd.io.packets.Handshake;
import net.portalblock.maintmotd.io.packets.PingPacket;
import net.portalblock.maintmotd.io.packets.StatusRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class PacketHandler extends AbstractHandler {

    private DataInputStream dis;
    private DataOutputStream dos;
    private ActiveConnection ac;

    public PacketHandler(DataInputStream dis, DataOutputStream dos, ActiveConnection ac) {
        this.dis = dis;
        this.dos = dos;
        this.ac = ac;
    }

    @Override
    public void handle(Handshake h) {
        System.out.println("Handshook");
        ac.setState(ActiveConnection.State.STAUS);
    }

    @Override
    public void handle(StatusRequest sr) {
        String json = "{\"version\": {\"name\": \"MaintMotd\",\"protocol\": 0},\"players\": {\"max\": 100,\"online\": 5,\"sample\":[{\"name\":\"Back up soon!\", \"id\":\"\"}]},\"description\": {\"text\":\"Hello world\"}}";
        Utils.writeVarInt(json.getBytes().length+4, dos);
        Utils.writeVarInt(0x00, dos);
        Utils.writeString(json, dos);
    }

    @Override
    public void handle(PingPacket pp) {
        pp.write(dos);
    }
}
