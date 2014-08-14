package net.portalblock.maintmotd.io.packets;

import net.portalblock.maintmotd.io.abstracts.AbstractHandler;
import net.portalblock.maintmotd.io.abstracts.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class StatusRequest implements AbstractPacket {

    @Override
    public AbstractPacket read(DataInputStream dis) {
        return this;
    }

    @Override
    public void write(DataOutputStream dos) {

    }

    @Override
    public void handle(AbstractHandler handler) {
        handler.handle(this);
    }
}
