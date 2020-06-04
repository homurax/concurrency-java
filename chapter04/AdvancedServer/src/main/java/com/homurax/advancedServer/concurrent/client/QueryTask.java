package com.homurax.advancedServer.concurrent.client;

import com.homurax.advancedServer.common.Constants;
import com.homurax.advancedServer.wdi.data.WDI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class QueryTask implements Runnable {

    private final List<WDI> data;
    private final String username;

    public QueryTask(List<WDI> data, String username) {
        this.data = data;
        this.username = username;
    }

    @Override
    public void run() {
        Random randomGenerator = new Random();

        try (Socket echoSocket = new Socket("localhost", Constants.CONCURRENT_PORT);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            WDI wdi = data.get(randomGenerator.nextInt(data.size()));

            StringWriter writer = new StringWriter();
            writer.write("q");
            writer.write(";");
            writer.write(username);
            writer.write(";");
            writer.write(String.valueOf(5));
            writer.write(";");
            writer.write(wdi.getCountryCode());
            writer.write(";");
            writer.write(wdi.getIndicatorCode());

            String command = writer.toString();
            out.println(command);
            String output = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
