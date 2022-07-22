import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        final int serverPortNIO = 45301;
        long startTime;

        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", serverPortNIO);
            final SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(inetSocketAddress);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Ожидается ввод пользователя");
                String userInput = scanner.nextLine();
                System.out.println("Ввод пользователя: " + userInput);
                if (userInput.equals("end")) {
                    break;
                }

                socketChannel.write(ByteBuffer.wrap(userInput.getBytes(StandardCharsets.UTF_8)));
                startTime = System.currentTimeMillis();
                System.out.println("Ожидание решения от сервера");
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println("Текст без пробелов: " + new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
                System.out.println("Ожидание ответа составило: " + (System.currentTimeMillis() - startTime) + " мс.");
            }
        } catch (Exception e) {
            System.out.println("Ooops :(");
        }
    }
}
