package client;

import java.io.DataInputStream;
import java.net.Socket;

public class MessageHandler extends Thread {

    private DataInputStream fromServerStream;
    private Socket socket;

    public MessageHandler(DataInputStream fromServerStream, Socket socket) {
        this.fromServerStream = fromServerStream;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = fromServerStream.readUTF();
                System.out.println(message);
                if (message.equals("bye")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al recibir mensajes del servidor");
            e.printStackTrace();
        }
    }
}
