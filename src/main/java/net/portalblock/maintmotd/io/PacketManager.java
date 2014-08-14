package net.portalblock.maintmotd.io;

import net.portalblock.maintmotd.io.abstracts.AbstractHandler;
import net.portalblock.maintmotd.io.abstracts.AbstractPacket;
import net.portalblock.maintmotd.io.packets.Handshake;
import net.portalblock.maintmotd.io.packets.StatusRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class PacketManager {
    private static Map<Integer, Class<? extends AbstractPacket>> packets = new HashMap<Integer, Class<? extends AbstractPacket>>();
    static {
        packets.put(15, Handshake.class);
        packets.put(254, StatusRequest.class);
    }

    public static AbstractPacket getPacket(int id){
        if(!packets.containsKey(id)){
            throw new UnsupportedOperationException("That packet is not yet implemented!");
        }
        try{
            return packets.get(id).newInstance();
        }catch (Exception e){

        }
        return null;
    }

}
