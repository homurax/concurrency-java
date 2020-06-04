package com.homurax.advancedServer.serial.server;

import com.homurax.advancedServer.common.Command;
import com.homurax.advancedServer.common.Constants;
import com.homurax.advancedServer.serial.command.ErrorCommand;
import com.homurax.advancedServer.serial.command.QueryCommand;
import com.homurax.advancedServer.serial.command.ReportCommand;
import com.homurax.advancedServer.serial.command.StopCommand;
import com.homurax.advancedServer.wdi.data.WDIDAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        WDIDAO dao = WDIDAO.getDAO();
        boolean stopServer = false;

        System.out.println("Initialization completed.");

        try {
            serverSocket = new ServerSocket(Constants.SERIAL_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        do {
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String line = in.readLine();
                Command command;
                String[] commandData = line.split(";");
                System.out.println("Command: " + commandData[0]);

                switch (commandData[0]) {
                    case "q":
                        System.out.println("Query");
                        command = new QueryCommand(commandData);
                        break;
                    case "r":
                        System.out.println("Report");
                        command = new ReportCommand(commandData);
                        break;
                    case "z":
                        System.out.println("Stop");
                        command = new StopCommand(commandData);
                        stopServer = true;
                        break;
                    default:
                        System.out.println("Error");
                        command = new ErrorCommand(commandData);
                        break;
                }

                String response = command.execute();
                System.out.println(response);
                out.println(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!stopServer);
    }

}
