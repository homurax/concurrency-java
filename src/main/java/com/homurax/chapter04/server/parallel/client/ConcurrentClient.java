package com.homurax.chapter04.server.parallel.client;

import com.homurax.chapter03.server.wdi.data.WDI;
import com.homurax.chapter03.server.wdi.data.WDIDAO;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;


public class ConcurrentClient implements Runnable {

    private String username;

    private ThreadPoolExecutor executor;

    public ConcurrentClient(String username, ThreadPoolExecutor executor) {
        this.username = username;
        this.executor = executor;
    }

    @Override
    public void run() {
        WDIDAO dao = WDIDAO.getDAO();
        List<WDI> data = dao.getData();
        Random randomGenerator = new Random();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                QueryTask task = new QueryTask(data, username);
                executor.submit(task);
            }
            ReportTask task = new ReportTask(data, username);
            executor.submit(task);
        }

        long end = System.currentTimeMillis();
        System.out.println("Total Time: "
                + ((end - start) / 1000)
                + " seconds.");

    }


}
