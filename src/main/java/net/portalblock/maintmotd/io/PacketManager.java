package net.portalblock.maintmotd.io;

import net.portalblock.maintmotd.io.abstracts.AbstractPacket;
import net.portalblock.maintmotd.io.packets.Handshake;
import net.portalblock.maintmotd.io.packets.StatusRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class PacketManager {
    private static Map<Integer, Class<? extends AbstractPacket>> handshakePackets = new HashMap<Integer, Class<? extends AbstractPacket>>();
    private static Map<Integer, Class<? extends AbstractPacket>> statusPackets = new HashMap<Integer, Class<? extends AbstractPacket>>();
    static {
        //Handshake
        handshakePackets.put(0x00, Handshake.class);

        //Status
        statusPackets.put(0x00, StatusRequest.class);
    }

    public static AbstractPacket getPacket(int id, ActiveConnection.State s){
        try {
            switch (s) {
                case HANDSHAKE:
                    return getPacketFromMap(handshakePackets, id).newInstance();
                case STAUS:
                    return getPacketFromMap(statusPackets, id).newInstance();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static Class<? extends AbstractPacket> getPacketFromMap(Map<Integer, Class<? extends AbstractPacket>> map, int id){
        return map.get(id);
    }

}
