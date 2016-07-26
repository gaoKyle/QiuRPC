package com.qiusuoba.nettyrpc.protocol;  

import java.io.ByteArrayOutputStream;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.qiusuoba.nettyrpc.common.Constants;

/**
 *响应编码
 *@Author:caimin 
 *@Since:2016年7月25日  
 *@Version:
 */
public class RpcResponseEncode extends SimpleChannelHandler {

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		RpcResponse response = (RpcResponse) e.getMessage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(16384);
		//先写入标示的魔数
		baos.write(Constants.MAGIC_BYTES);
		MySerializerFactory.getInstance(Constants.DEFAULT_RPC_CODE_MODE).encodeResponse(baos, response);
		ChannelBuffer buffer = ChannelBuffers.wrappedBuffer(baos.toByteArray());
		Channels.write(ctx, e.getFuture(), buffer);
	}

}
