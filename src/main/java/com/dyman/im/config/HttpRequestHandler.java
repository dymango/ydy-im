package com.dyman.im.config;

import com.dyman.im.cache.SocketConnectionCache;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;

/**
 * @author dyman
 * @describe
 * @date 2020/1/11
 */
@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try
        {
            String path = "D://index.txt";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        } catch (Exception e)
        {
            throw new IllegalStateException("unable to locate index.html", e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {
        String url = request.uri();
        int index = url.indexOf("?");
        String userId = url.substring(index+1, url.length());
        log.info(userId+"用户连接");
        if(request.uri().startsWith(wsUri))
        {
            SocketConnectionCache.put(userId, channelHandlerContext);
            request.setUri("/ws");
            channelHandlerContext.fireChannelRead(request.retain());
        }
//        else
//        {
//            if (HttpHeaders.is100ContinueExpected(request)) {
//                send100Continue(channelHandlerContext);
//            }
//            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
//            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
//            response.headers().set(
//                    HttpHeaders.Names.CONTENT_TYPE,
//                    "text/html; charset=UTF-8"
//            );
//            boolean keepAlive = HttpHeaders.isKeepAlive(request);
//            if(keepAlive)
//            {
//                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
//                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//            }
//            channelHandlerContext.write(response);
//            if(channelHandlerContext.pipeline().get(SslHandler.class) == null)
//            {
//                channelHandlerContext.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
//            }
//            else
//            {
//                channelHandlerContext.write(new ChunkedNioFile(file.getChannel()));
//            }
//            ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//            if(!keepAlive)
//            {
//                future.addListener(ChannelFutureListener.CLOSE);
//            }
//        }
    }

    private static void send100Continue(ChannelHandlerContext ctx)
    {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
