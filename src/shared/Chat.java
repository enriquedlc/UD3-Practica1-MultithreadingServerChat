package shared;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chat {

    private List<String> messages = null;
    private List<DataOutputStream> clients = null;
    private final int INITIAL_LENGTH = 5;

    public Chat() {
        this.messages = new ArrayList<String>(INITIAL_LENGTH);
        this.clients = new ArrayList<DataOutputStream>(INITIAL_LENGTH);
    }

    synchronized public void addMessage(String message) {
        messages.add(message);
    }
    
    synchronized public void addClient(DataOutputStream client) {
        clients.add(client);
    }

    public void sendToAllClients(String message) {
        for (DataOutputStream client : clients) {
            try {
                client.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public String getChat() {
        String output = "-- CHAT --\n";
        for (String message : messages) {
            output += message + "\n";
        }
        return output;
    }

    /* getter */
    public List<String> getMessages() {
        return messages;
    }

    public int size() {
        return messages.size();
    }

}