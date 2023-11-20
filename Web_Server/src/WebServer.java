/* Java Libraries */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/* Import Classes */
import server.Account;
import server.ClientHandler;

public class WebServer {
    private List<Account> clientAccounts;

    private void readDataAccounts() throws FileNotFoundException{
        // Get File Locations
        try {
            String currentDirectory = System.getProperty("user.dir");
            String fileLocation = "Web_Server/resources/File.txt";
            File file = new File(currentDirectory, fileLocation);

            // Find File 
            List<Account> clientAccounts = new ArrayList<Account>();
            while(!file.exists()){
                String[] temp_file_split = fileLocation.split("/");
                String[] file_split = Arrays.copyOfRange(temp_file_split, 1,temp_file_split.length);
                
                StringBuffer buffer_file = new StringBuffer("/") ;
                for(String fl : file_split){
                    buffer_file.append(fl);
                }
                fileLocation = buffer_file.toString();
                file = new File(currentDirectory,fileLocation);
            }

            // Read File
            Scanner readFile = new Scanner(file);
            int line = 0;
            while(readFile.hasNextLine()){
                // Skip First Line
                String data = readFile.nextLine();
                if(line == 0){
                    line++;
                    continue;
                }
                String [] dataArray = data.split(", ");
                Account temp_account = new Account(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]));
                clientAccounts.add(temp_account);
            }
            this.clientAccounts = clientAccounts;
            readFile.close();
        
        } 
        catch (FileNotFoundException  e) {
            System.out.print("File not found");
            e.printStackTrace();
        } 
    }


    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            // Read File
            readDataAccounts();
            while (true) {
                System.out.println("Waiting for a client to connect...");
                System.out.println("http://localhost:5000");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client...");

                // Create a new thread to handle the client request
                ClientHandler clientHandler = new ClientHandler(clientSocket,clientAccounts);
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
