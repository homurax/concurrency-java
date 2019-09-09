package com.homurax.chapter04.server.parallel.client;

import com.homurax.chapter03.server.common.Constants;
import com.homurax.chapter03.server.wdi.data.WDI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ReportTask implements Runnable {

    private List<WDI> data;
    private String username;

    public ReportTask(List<WDI> data, String username) {
        this.data = data;
        this.username = username;
    }

    @Override
    public void run() {
        Random randomGenerator = new Random();

        try (Socket echoSocket = new Socket("localhost", Constants.CONCURRENT_PORT);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))) {


            WDI wdi = data.get(randomGenerator.nextInt(data.size()));

            StringWriter writer = new StringWriter();
            writer.write("r");
            writer.write(";");
            writer.write(username);
            writer.write(";");
            writer.write(String.valueOf(10));
            writer.write(";");
            writer.write(wdi.getIndicatorCode());

            String command = writer.toString();
            out.println(command);
            String output = in.readLine();
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
