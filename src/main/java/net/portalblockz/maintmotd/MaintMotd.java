package net.portalblockz.maintmotd;

import net.portalblockz.maintmotd.packets.Handshake;

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


    }

    public AbstractPacket createPacket(ServerInboundHandler.ConnState state, int id){
        try{
            switch (state){
                case HANDSHAKE: return getPacketFromMap(handshakePackets, id).newInstance();
                case STATUS: return getPacketFromMap(statusPackets, id).newInstance();
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    private static Class<? extends AbstractPacket> getPacketFromMap(Map<Integer, Class<? extends AbstractPacket>> map, int id){
        try{
            return map.get(id);
        }catch (Exception e){
            return null;
        }
    }

}
