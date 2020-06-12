package com.homurax.synchronization.completable;

import com.homurax.synchronization.data.Product;
import com.homurax.synchronization.data.ProductLoader;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LoadTask implements Supplier<List<Product>> {

    private final Path path;

    public LoadTask(Path path) {
        this.path = path;
    }

    @Override
    public List<Product> get() {
        System.out.println(LocalDateTime.now() + ": LoadTask: starting....");
        List<Product> productList = null;
        try {
            productList = Files.walk(path, FileVisitOption.FOLLOW_LINKS).parallel()
                    .filter(f -> f.toString().endsWith(".txt"))
                    .map(ProductLoader::load)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + ": LoadTask: end");
        return productList;
    }

}
