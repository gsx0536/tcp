package com.tieda.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.util.Scanner;


public class Client {

    public static final int port=7777;
    //private static final String ip="192.168.10.94";
   // public static final String ip="180.167.18.154";
    public static final String ip="192.168.10.246";
    //private static final String ip="localhost";

    private static final byte[] b={(byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0F, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0xFF, (byte)0xFF};

    public static void main(String[] args) throws Exception{
        client();
    }



    public static void client(){
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            Channel channel = channelFuture.channel();
            while (true) {
                ByteBuf buffer = Unpooled.copiedBuffer(b);
                channel.writeAndFlush(buffer);
                Thread.sleep(5000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     **/
    public static String byteArrToBinStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        String temp="";
        for (int i = 0; i < b.length; i++) {
            temp=Long.toString(b[i] & 0xff, 2);
            int len=temp.length();
            for(int n=8;n>len;n--){
                temp="0"+temp;
            }
            result.append( temp+ ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }
}
