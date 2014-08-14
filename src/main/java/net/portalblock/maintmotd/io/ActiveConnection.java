package net.portalblock.maintmotd.io;

import net.portalblock.maintmotd.Utils;
import net.portalblock.maintmotd.io.abstracts.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class ActiveConnection extends Thread{
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean respondOnNext;
    private State state;

    public void setState(State state){
        this.state = state;
    }

    public ActiveConnection(Socket s){
        respondOnNext = false;
        this.s = s;
        this.state = State.HANDSHAKE;
        try{
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());
        }catch (IOException e){
        }
        start();
    }

    @Override
    public void run() {
        try{
            while (true){
                Utils.readVarInt(dis);
                int id = Utils.readVarInt(dis);
                System.out.println(id);
                AbstractPacket p = PacketManager.getPacket(id, this.state);
                if(p != null){
                    p.read(dis);
                    p.handle(new PacketHandler(dis, dos, this));
                }else{
                    System.out.println("Null packet from PM");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public enum State{
        HANDSHAKE,
        GAME,
        STAUS,
        LOGIN
    }
}
