package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import shared.Chat;

import server.threads.ClientHandler;
import shared.Constants;

public class ServerApp {
    public static void main(String[] args) {

        /* Creamos el socket en nulo, para poder cerrarlo en el finnally */
        ServerSocket serverSocket = null;

        try {

            /* Instanciamos el ServerSocket en el puerto que se encuentra en la Constante */
            serverSocket = new ServerSocket(Constants.SERVER_PORT);
            Chat chat = new Chat();

            /*
             * Después, instanciamos un ObjectInputStream para recibir el objeto del
             * cliente, para poder
             * procesarlo.
             */


            while (true) {

                /* Hacemos que el servidor escuche peticiones */
                System.out.println("========================");
                System.out.println("Esperando por cliente...");
                System.out.println("========================");
                Socket clientSocket = serverSocket.accept();
                
                /*
                * En primer lugar, desde el scoket, cogeremos desde que dirección se ha
                * "conectado" el cliente, y la mostraremos.
                */
                System.out.println("================================================");
                System.out.println("La conexión se ha realizado con " + clientSocket.getInetAddress().toString());
                System.out.println("================================================");

                DataInputStream fromClientStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream toClientStream = new DataOutputStream(clientSocket.getOutputStream());
                
                chat.addClient(toClientStream);

                ClientHandler newClient = new ClientHandler(toClientStream, fromClientStream, chat);
                System.out.println("Leyendo datos del cliente...");
                newClient.start();
            }

            /* Cuando cancelamos el proceso del serivdor, se cerrará */

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
