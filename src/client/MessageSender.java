package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MessageSender extends Thread {

    private DataOutputStream toServerStream;
    private Scanner scanner = new Scanner(System.in);

    public MessageSender(DataOutputStream toServerStream) {
        this.toServerStream = toServerStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = scanner.nextLine();
                toServerStream.writeUTF(message);
                if (message.equals("bye")) {
                    break;
                }
            }
            // toServerStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
