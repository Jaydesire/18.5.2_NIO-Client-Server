import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {

        final int serverPortNIO = 45301;
        System.out.println("Сервер запущен...");
        try {
            final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", serverPortNIO));
            SocketChannel socketChannel = serverSocketChannel.accept();

            while (true) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                System.out.println("Сервер подключен...");


                while (socketChannel.isConnected()) {
                    int byteCount = socketChannel.read(inputBuffer);
                    if (byteCount == -1) {
                        break;
                    }

                    System.out.println("Ожидается ввод пользователя");
                        String userText = new String(inputBuffer.array(), 0, byteCount, StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        userText = userText.replaceAll(" ", "");
                        System.out.println("Текст без пробелов: " + userText);
                        socketChannel.write(ByteBuffer.wrap(userText.getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (Exception e) {
            System.out.println("Ooops :(");
        }
    }
}


