package net.portalblock.maintmotd;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by portalBlock on 8/13/2014.
 */
public class Utils {

    public static void writeString(String s, DataOutputStream dos)
    {
        Preconditions.checkArgument(s.length() <= Short.MAX_VALUE, "Cannot send string longer than Short.MAX_VALUE (got %s characters)", s.length());

        byte[] b = s.getBytes( Charsets.UTF_8 );
        writeVarInt( b.length, dos );
        writeBytes(b, dos);
    }

    public static String readString(DataInputStream dis)
    {
        int len = readVarInt(dis);
        Preconditions.checkArgument( len <= Short.MAX_VALUE, "Cannot receive string longer than Short.MAX_VALUE (got %s characters)", len );

        byte[] b = new byte[ len ];
        try{
            dis.read(b);
        }catch (IOException e){
            e.printStackTrace();
        }

        return new String( b, Charsets.UTF_8 );
    }



    public static int readVarInt(DataInputStream dis)
    {
        int i = 0;
        int j = 0;
        while (true)
        {
            int k = readByte(dis);

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5) throw new RuntimeException("VarInt too big");

            if ((k & 0x80) != 128) break;
        }

        return i;
    }

    public static void writeVarInt(int paramInt, DataOutputStream dos) {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                writeByte(paramInt, dos);
                return;
            }

            writeByte(paramInt & 0x7F | 0x80, dos);
            paramInt >>>= 7;
        }
    }

    private static void writeByte(int i, DataOutputStream dos){
        try{
            dos.writeByte(i);
            dos.flush();
        }catch (IOException e){

        }
    }

    private static void writeBytes(byte[] b, DataOutputStream dos){
        try{
            dos.write(b);
            dos.flush();
        }catch (IOException e){

        }
    }

    private static byte readByte(DataInputStream dis){
        byte b = 0;
        try{
            b = dis.readByte();
        }catch (IOException e){

        }
        return b;
    }

}
