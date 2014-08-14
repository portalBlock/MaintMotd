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

    public ActiveConnection(Socket s){
        respondOnNext = false;
        this.s = s;
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
                int id = Utils.readVarInt(dis);
                System.out.println(id);
                AbstractPacket p = PacketManager.getPacket(id);
                if(p != null){
                    p.read(dis);
                    p.handle(new PacketHandler(dis, dos));
                }else{
                    System.out.println("Null packet from PM");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
