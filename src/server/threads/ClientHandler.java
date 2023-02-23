package server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import shared.Chat;

public class ClientHandler extends Thread {

    private DataInputStream fromClientStream;
    private DataOutputStream toClientStream;
    private Chat chat;

    /* Constructor de ClientHandler */
    public ClientHandler(DataOutputStream toClientStream, DataInputStream fromClientStream, Chat chat) {
        this.fromClientStream = fromClientStream;
        this.toClientStream = toClientStream;
        this.chat = chat;
    }

    @Override
    public void run() {
        try {

            /*
             * Cuando el servidor recibe el mensaje, instanciará el ClientHandler,
             * al ocurrir esto, y hacer el .start(), empezará a leer la información
             * que recibiera desde el puerto
             */

            /*
             * Una vez tenemos todo preparado, entraremos en el bucle desde el que se
             * recibirán mensajes del cliente
             * hasta que este escriba la palabra "bye", una vez lo haga, el server dirá
             * "goodbye" a modo de respuesta
             */
        
            String name = fromClientStream.readUTF();


            while (true) {

                String message = fromClientStream.readUTF();

                if (message.startsWith("message:")) {
                    DateFormat clientMessageTime = new SimpleDateFormat("HH:mm:ss");
                    String chatMessage = clientMessageTime.format(new Date()) + " " + name + ": " +
                    message.substring(message.indexOf(":") + 1);
                    chat.addMessage(chatMessage);
                    chat.sendToAllClients(chatMessage);
                } else if (message.equals("bye")) {
                    toClientStream.writeUTF("goodbye");
                    break;
                } else {
                    toClientStream.writeUTF("Mensaje no válido");
                }
            }

            System.out.println("-->" + name + " se ha ido.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
