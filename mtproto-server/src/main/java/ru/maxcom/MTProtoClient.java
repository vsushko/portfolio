package ru.maxcom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

import static ru.maxcom.AppUtils.HOST_NAME;
import static ru.maxcom.AppUtils.PORT;

/**
 * MTProtoClient
 *
 * @author vsushko
 */
public class MTProtoClient {

    /**
     * Reads socket channel
     *
     * @param socketChannel the channel
     */
    private static void startRead(final AsynchronousSocketChannel socketChannel) {
        final ByteBuffer buffer = ByteBuffer.allocate(2048);

        socketChannel.read(buffer, socketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
            @Override
            public void completed(Integer result, AsynchronousSocketChannel channel) {
                buffer.flip();

                String serverCommand = Charset.defaultCharset().decode(buffer).toString();
                if (serverCommand.startsWith(Command.RES_PQ.getValue())) {
                    System.out.println("Received res_pq command with random number: "
                            + serverCommand.substring(Command.RES_PQ.getValue().length(), serverCommand.length()));
                    serverCommand = Command.RES_PQ.getValue();
                }
                System.out.println("Received command from server: " + serverCommand);

                Command inputCommand = Command.get(serverCommand);
                Command nextCommand = Command.UNKNOWN;

                switch (inputCommand) {
                    case RES_PQ:
                        nextCommand = Command.REQ_DH_PARAMS;
                        break;
                    case SERVER_DH_PARAMS_OK:
                        closeChannel(channel);
                        break;
                    case UNKNOWN:
                        closeChannel(channel);
                        break;
                    default:
                        break;
                }

                // send command to server
                startWrite(channel, nextCommand.getValue());
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
                System.out.println("fail to read message from server");
            }
        });
    }

    /**
     * Closes connection
     *
     * @param channel the channel
     */
    private static void closeChannel(AsynchronousSocketChannel channel) {
        try {
            channel.close();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to socket channel
     *
     * @param socketChannel the channel
     * @param command       the command
     */
    private static void startWrite(final AsynchronousSocketChannel socketChannel, final String command) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        buffer.put(command.getBytes());
        buffer.flip();

        socketChannel.write(buffer, socketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
            @Override
            public void completed(Integer result, AsynchronousSocketChannel channel) {
                System.out.println("Command " + command + " was sent to server");
                startRead(socketChannel);
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
                System.out.println("Fail to write the command to server");
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
        AsynchronousSocketChannel sockChannel = AsynchronousSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(HOST_NAME, PORT);

        sockChannel.connect(address, sockChannel, new CompletionHandler<Void, AsynchronousSocketChannel>() {
            @Override
            public void completed(Void result, AsynchronousSocketChannel attachment) {
                startWrite(attachment, Command.REQ_PQ.getValue());
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
                System.out.println("fail to connect to server");
            }
        });

        while (true) {
            Thread.sleep(1000);
        }
    }
}
