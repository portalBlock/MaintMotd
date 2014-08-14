package net.portalblock.maintmotd.io.abstracts;

import net.portalblock.maintmotd.io.packets.Handshake;
import net.portalblock.maintmotd.io.packets.StatusRequest;

/**
 * Created by portalBlock on 8/13/2014.
 */
public abstract class AbstractHandler {

    public void handle(Handshake h){}

    public void handle(StatusRequest sr){}

}
