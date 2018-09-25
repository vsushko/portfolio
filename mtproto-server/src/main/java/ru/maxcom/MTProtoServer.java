package ru.maxcom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

import static ru.maxcom.AppUtils.HOST_NAME;
import static ru.maxcom.AppUtils.PORT;

/**
 * MTProtoServer
 *
 * @author vsushko
 */
public class MTProtoServer {

    /**
     * Reads the channel
     *
     * @param socketChannel the channel
     */
    private static void startRead(AsynchronousSocketChannel socketChannel) {
        final ByteBuffer buffer = ByteBuffer.allocate(2048);

        // read message from client
        socketChannel.read(buffer, socketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
            @Override
            public void completed(Integer result, AsynchronousSocketChannel channel) {
                buffer.flip();

                String clientInput = Charset.defaultCharset().decode(buffer).toString();
                if (!clientInput.isEmpty()) {
                    System.out.println("Received command from client: " + clientInput);
                    Command inputCommand = Command.get(clientInput);
                    String nextCommand = Command.UNKNOWN.getValue();

                    if (inputCommand != null) {
                        switch (inputCommand) {
                            case REQ_PQ:
                                nextCommand = Command.RES_PQ.getValue() + AppUtils.generateRandomNumber();
                                break;
                            case REQ_DH_PARAMS:
                                nextCommand = Command.SERVER_DH_PARAMS_OK.getValue();
                                break;
                        }
                        // send the message to client
                        startWrite(channel, nextCommand);
                    }
                }
                // start to read next message again
                startRead(channel);
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
                System.out.println("fail to read message from client");
            }
        });
    }

    /**
     * Writes to channel
     *
     * @param socketChannel the channel
     * @param command       command
     */
    private static void startWrite(AsynchronousSocketChannel socketChannel, final String command) {
        ByteBuffer buffer = ByteBuffer.wrap(command.getBytes());
        socketChannel.write(buffer, socketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
            @Override
            public void completed(Integer result, AsynchronousSocketChannel channel) {
                System.out.println("Command " + command + " was sent to server");
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
                // fail to write message to client
                System.out.println("Fail to write message to client");
            }
        });
    }

    /**
     * The main method
     *
     * @param args args
     * @throws IOException          io exception
     * @throws InterruptedException interrupted exception
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress(HOST_NAME, PORT);
        AsynchronousServerSocketChannel serverSock = AsynchronousServerSocketChannel.open().bind(address);

        serverSock.accept(serverSock, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
            @Override
            public void completed(AsynchronousSocketChannel sockChannel, AsynchronousServerSocketChannel serverSock) {
                // a connection is accepted, start to accept next connection
                serverSock.accept(serverSock, this);
                // start to read message from the client
                startRead(sockChannel);
            }

            @Override
            public void failed(Throwable exc, AsynchronousServerSocketChannel serverSock) {
                System.out.println("fail to accept a connection");
            }
        });

        while (true) {
            Thread.sleep(10000);
        }
    }
}
