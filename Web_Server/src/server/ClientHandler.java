package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;



//A shared resource/class. 
class Shared  
{ 
    static int count = 0; 
} 

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<Account> accounts = new ArrayList<Account>();
    static List<Account> clientAccounts;
    static Semaphore semaphore = new Semaphore(1);

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ClientHandler(Socket clientSocket, List<Account> accounts){
        this.clientSocket = clientSocket;
        this.accounts = accounts;
        clientAccounts = this.accounts;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream();
            String request = in.readLine();

            if (request != null) {
                if (request.startsWith("GET")) {
                    // Handle GET request
                    handleGetRequest(out);
                } else if (request.startsWith("POST")) {
                    // Handle POST request
                    handlePostRequest(in, out);
                }
            }

            // Close resources
            in.close();
            out.close();
            clientSocket.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        semaphore.release();
    }

    private static void handleGetRequest(OutputStream out) throws IOException {
        // Original GET request handling code
        String response = "HTTP/1.1 200 OK\r\n\r\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Concordia Transfers</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Welcome to Concordia Transfers</h1>\n" +
                "<p>Select the account and amount to transfer</p>\n" +
                "\n" +
                "<form action=\"/submit\" method=\"post\">\n" +
                "        <label for=\"account\">Withdraw from Account :</label>\n" +
                "        <input type=\"text\" id=\"account\" name=\"account\"><br><br>\n" +
                "\n" +
                "        <label for=\"toAccount\">Deposit to Account:</label>\n" +
                "        <input type=\"text\" id=\"toAccount\" name=\"toAccount\"><br><br>\n" +
                "\n" +
                "        <label for=\"value\">Withdraw Value:</label>\n" +
                "        <input type=\"text\" id=\"value\" name=\"value\"><br><br>\n" +
                "\n" +
                "        <input type=\"submit\" value=\"Submit\">\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>\n";
        out.write(response.getBytes());
        out.flush();
    }

    private static void handlePostRequest(BufferedReader in, OutputStream out) throws IOException {
        // Original POST request handling code
        StringBuilder requestBody = new StringBuilder();
        int contentLength = 0;
        String line;

        // Read headers to get content length
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(line.substring(line.indexOf(' ') + 1));
            }
        }

        // Read the request body based on content length
        for (int i = 0; i < contentLength; i++) {
            requestBody.append((char) in.read());
        }

        System.out.println(requestBody.toString());
        // Parse the request body as URL-encoded parameters
        String[] params = requestBody.toString().split("&");
        /// String account = null, value = null, toAccount = null, toValue = null;

        // Accounts to Save Data into
        Account account1 = new Account(new Random().nextInt(1000,50000));
        Account account2 = new Account(new Random().nextInt(1000,50000));
        int previousAccount1Val = 0, previousAccount2Val = 0;


        for (String param : params) {
            String[] parts = param.split("=");
            if (parts.length == 2) {
                String key = URLDecoder.decode(parts[0], "UTF-8");
                String val = URLDecoder.decode(parts[1], "UTF-8");


                switch (key) {
                    /* 
                    case "account":
                        switch (val) {
                            case "123":
                                account1 = clientAccount1;
                                break;
                            case "321":
                                account1 = clientAccount2;
                                break;
                            case "432":
                                account1 = clientAccount3;
                                break;
                            default:
                                account1.setId(Integer.parseInt(val));
                                break;
                        }
                        break;
                    case "toAccount":
                        switch (val) {
                            case "123":
                                account2 = clientAccount1;
                                break;
                            case "321":
                                account2 = clientAccount2;
                                break;
                            case "432":
                                account2 = clientAccount3;
                                break;
                            default:
                                account2.setId(Integer.parseInt(val));
                                break;
                        }
                        break;
                    
                    */
                    case "account" :
                        
                        for(int i = 0; i<clientAccounts.size(); i++){
                            if(clientAccounts.get(i).getId() == Integer.parseInt(val)){
                                account1 = clientAccounts.get(i);
                                break;
                            }
                        }
                        if (account1.getId() == 1000){
                            account1.setId(Integer.parseInt(val));
                        }
                        break;
                    
                    case "toAccount":
                        for(int i = 0; i<clientAccounts.size(); i++){
                            if(clientAccounts.get(i).getId() == Integer.parseInt(val)){
                                account2 = clientAccounts.get(i);
                                break;
                            }
                        }
                        if (account2.getId() == 1000){
                            account2.setId(Integer.parseInt(val));
                        }
                        break;

                    case "value":
                        previousAccount1Val = account1.getBalance();
                        previousAccount2Val = account2.getBalance();
                        
                        if(previousAccount1Val > Integer.parseInt(val)){
                            account1.withdraw(Integer.parseInt(val));
                            account2.deposit(Integer.parseInt(val));
                            break;
                        }
                        else{
                            System.out.println("Unable to make withdrawl, please choose a smaller amount");
                            break;
                        }
                }
            }
        }

        // Create the response
        String responseContent = "<html><body><h1>Thank you for using Concordia Transfers</h1>" +
                "<h2>Received Form Inputs:</h2>"+
                "<p>Source Account ID: " + account1.getId() + "</p>" +
                "<p>Source Previous Balance Value: " + previousAccount1Val + "</p>" +
                "<p>Source Current Balance Value: " + account1.getBalance() + "</p>" +
                "<p>Destination Account ID: " + account2.getId() + "</p>" +
                "<p>Destination Previous Balance Value: " + previousAccount2Val + "</p>" +
                "<p>Destination Current Balance Value: " + account2.getBalance() + "</p>" +
                "</body></html>";
        

        // Respond with the received form inputs
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseContent.length() + "\r\n" +
                "Content-Type: text/html\r\n\r\n" +
                responseContent;

        out.write(response.getBytes());
        out.flush();
    }
}
