package net.portalblock.maintmotd;

import net.portalblock.maintmotd.io.ActiveConnection;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class MaintMotd {
    
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(25565);
            while (true){
                new ActiveConnection(s.accept());
            }
        }catch (IOException e){

        }

    }

}
