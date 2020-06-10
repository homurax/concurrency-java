package com.homurax.searchWithoutIndexing.serial.main;

import com.homurax.searchWithoutIndexing.data.Product;
import com.homurax.searchWithoutIndexing.data.ProductLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class SerialLoaderAccumulator implements BiConsumer<ArrayList<Product>, Path> {

    @Override
    public void accept(ArrayList<Product> list, Path path) {
        Product product = ProductLoader.load(path);
        list.add(product);
    }

}
