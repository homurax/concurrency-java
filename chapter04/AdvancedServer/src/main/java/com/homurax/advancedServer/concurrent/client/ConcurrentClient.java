package com.homurax.advancedServer.concurrent.client;

import com.homurax.advancedServer.wdi.data.WDI;
import com.homurax.advancedServer.wdi.data.WDIDAO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentClient implements Runnable {

    private final String username;

    private final ThreadPoolExecutor executor;

    public ConcurrentClient(String username, ThreadPoolExecutor executor) {
        this.username = username;
        this.executor = executor;
    }

    @Override
    public void run() {

        WDIDAO dao = WDIDAO.getDAO();
        List<WDI> data = dao.getData();

        LocalDateTime start = LocalDateTime.now();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                QueryTask task = new QueryTask(data, username);
                executor.submit(task);
            }
            ReportTask task = new ReportTask(data, username);
            executor.submit(task);
        }

        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Total Time: " + duration);
    }


}
