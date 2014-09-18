/*
 * Copyright (c) 2012, md_5. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * You may not use the software for commercial software hosting services without
 * written permission from the author.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.portalblockz.maintmotd;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;

/**
 * Created by portalBlock on 9/2/2014.
 */
public abstract class AbstractPacket {

    public abstract void write(ByteBuf buf);

    public abstract void read(ByteBuf buf);

    public abstract void handle(ServerInboundHandler handler);


    //Credit to Bungee devs for the following methods.

    public static void writeVarInt(int value, ByteBuf output){
        int part;
        while ( true ) {
            part = value & 0x7F;

            value >>>= 7;
            if ( value != 0 ) {
                part |= 0x80;
            }

            output.writeByte( part );

            if ( value == 0 ) {
                break;
            }
        }
    }

    public static void writeString(String s, ByteBuf buf)  {
        Preconditions.checkArgument(s.length() <= Short.MAX_VALUE, "Cannot send string longer than Short.MAX_VALUE (got %s characters)", s.length());

        byte[] b = s.getBytes( Charsets.UTF_8 );
        writeVarInt( b.length, buf );
        buf.writeBytes( b );
    }

    public static String readString(ByteBuf buf) {
        int len = readVarInt( buf );
        Preconditions.checkArgument( len <= Short.MAX_VALUE, "Cannot receive string longer than Short.MAX_VALUE (got %s characters)", len );

        byte[] b = new byte[ len ];
        buf.readBytes( b );

        return new String( b, Charsets.UTF_8 );
    }

    public static void writeArray(byte[] b, ByteBuf buf) {
        Preconditions.checkArgument( b.length <= Short.MAX_VALUE, "Cannot send array longer than Short.MAX_VALUE (got %s bytes)", b.length );

        buf.writeShort( b.length );
        buf.writeBytes( b );
    }

    public static byte[] readArray(ByteBuf buf) {
        short len = buf.readShort();
        Preconditions.checkArgument( len <= Short.MAX_VALUE, "Cannot receive array longer than Short.MAX_VALUE (got %s bytes)", len );

        byte[] ret = new byte[ len ];
        buf.readBytes( ret );
        return ret;
    }

    public static void writeStringArray(String[] s, ByteBuf buf) {
        writeVarInt( s.length, buf );
        for ( String str : s ) {
            writeString( str, buf );
        }
    }

    public static String[] readStringArray(ByteBuf buf) {
        int len = readVarInt( buf );
        String[] ret = new String[ len ];
        for ( int i = 0; i < ret.length; i++ ) {
            ret[i] = readString( buf );
        }
        return ret;
    }

    public static int readVarInt(ByteBuf input){
        int i = 0;
        int j = 0;
        while (true) {
            int k = input.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128) break;
        }
        return i;
    }

}
