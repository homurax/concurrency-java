package com.homurax.searchWithoutIndexing.concurrent.main;

import com.homurax.searchWithoutIndexing.data.Product;
import com.homurax.searchWithoutIndexing.data.ProductLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

public class ConcurrentLoaderAccumulator implements BiConsumer<List<Product>, Path> {

    @Override
    public void accept(List<Product> list, Path path) {
        Product product = ProductLoader.load(path);
        list.add(product);
    }

}
