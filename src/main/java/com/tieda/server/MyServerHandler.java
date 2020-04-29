package com.tieda.server;

import com.tieda.db.SqlOperation;
import com.tieda.entity.Receive;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf>//ChannelInboundHandlerAdapter
{

    static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    public void channelRead0(final ChannelHandlerContext ctx, final ByteBuf msg) {
        /*group.submit(new Callable<Object>() {
            public Object call() throws Exception {
                try {
                    *//*ByteBuf buf = (ByteBuf) msg;
                    byte[] bytes = new byte[buf.readableBytes()];
                    buf.readBytes(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println(Thread.currentThread().getName()+"----"+body);*//*
                    *//*Receive receive = new Receive();
                    receive.setInfor(msg);
                    receive.setDate(new Date());
                    receive.setIp(ctx.channel().remoteAddress().toString());
                    SqlOperation.insertData(receive);*//*
                    System.out.println("readserver----"+msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });*/
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        //System.out.println(Arrays.toString(buffer));
        System.out.println(message);
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(message+ " ", Charset.forName("utf-8"));
        ctx.writeAndFlush(responseByteBuf);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
