/* Java Libraries */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* Import Classes */
import server.ClientHandler;

public class WebServer {

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                System.out.println("Waiting for a client to connect...");
                System.out.println("http://localhost:5000");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client...");

                // Create a new thread to handle the client request
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
