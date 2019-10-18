package com.homurax.chapter11.synchronization.completable;

import com.homurax.chapter11.structure.hash.data.Product;
import com.homurax.chapter11.structure.hash.data.Review;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableMain {

    public static void main(String[] args) {

        Path file = Paths.get("data", "category");

        System.out.println(LocalDateTime.now() + ": Main: Loading products after three seconds....");

        LoadTask loadTask = new LoadTask(file);

        CompletableFuture<List<Product>> loadFuture = CompletableFuture
				.supplyAsync(loadTask, CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS));

        System.out.println(LocalDateTime.now() + ": Main: Then apply for search....");

        CompletableFuture<List<Product>> completableSearch = loadFuture.thenApplyAsync(new SearchTask("love"));

        CompletableFuture<Void> completableWrite = completableSearch.thenAcceptAsync(new WriteTask());

        completableWrite.exceptionally(ex -> {
            System.out.println(LocalDateTime.now() + ": Main: Exception " + ex.getMessage());
            return null;
        });

        System.out.println(LocalDateTime.now() + ": Main: Then apply for users....");

        CompletableFuture<List<String>> completableUsers = loadFuture
                .thenApplyAsync(resultList -> {

                    System.out.println(LocalDateTime.now() + ": Main: Completable users: start");

                    List<String> users = resultList.stream()
                            .flatMap(p -> p.getReviews().stream())
                            .map(Review::getUser)
                            .distinct()
                            .collect(Collectors.toList());

                    System.out.println(LocalDateTime.now() + ": Main: Completable users: end");
                    return users;

                });

        System.out.println(LocalDateTime.now() + ": Main: Then apply for best rated product....");

        CompletableFuture<Product> completableProduct = loadFuture
                .thenApplyAsync(resultList -> {

                    Product maxProduct = null;
                    double maxScore = 0.0;

                    System.out.println(LocalDateTime.now() + ": Main: Completable product: start");

                    for (Product product : resultList) {
                        if (!product.getReviews().isEmpty()) {
                            double score = product.getReviews().stream().mapToDouble(Review::getValue).average().getAsDouble();
                            if (score > maxScore) {
                                maxProduct = product;
                                maxScore = score;
                            }
                        }
                    }

                    System.out.println(LocalDateTime.now() + ": Main: Completable product: end");
                    return maxProduct;
                });


        System.out.println(LocalDateTime.now() + ": Main: Then apply for best selling product....");
        CompletableFuture<Product> completableBestSellingProduct = loadFuture
                .thenApplyAsync(resultList -> {
                    System.out.println(LocalDateTime.now() + ": Main: Completable best selling: start");

                    Product bestProduct = resultList.stream().min(Comparator.comparingLong(Product::getSalesRank)).orElse(null);

                    System.out.println(LocalDateTime.now() + ": Main: Completable best selling: end");
                    return bestProduct;

                });

        CompletableFuture<String> completableProductResult = completableBestSellingProduct
                .thenCombineAsync(
                        completableProduct,
                        (bestSellingProduct, bestRatedProduct) -> {
                            System.out.println(LocalDateTime.now() + ": Main: Completable product result: start");

                            String ret = "The best selling product is " + bestSellingProduct.getTitle() + "\n";

                            ret += "The best rated product is " + bestRatedProduct.getTitle();

                            System.out.println(LocalDateTime.now() + ": Main: Completable product result: end");
                            return ret;
                        });


        System.out.println(LocalDateTime.now() + ": Main: Waiting for results");

        completableProductResult.completeOnTimeout("TimeOut", 1, TimeUnit.SECONDS);


        CompletableFuture<Void> finalCompletableFuture = CompletableFuture
				.allOf(completableProductResult, completableUsers, completableWrite);

        finalCompletableFuture.join();

        try {
            System.out.println("Number of loaded products: " + loadFuture.get().size());
            System.out.println("Number of found products: " + completableSearch.get().size());
            System.out.println("Number of users: " + completableUsers.get().size());
            System.out.println("Best rated product: " + completableProduct.get().getTitle());
            System.out.println("Best selling product: " + completableBestSellingProduct.get().getTitle());
            System.out.println("Product result: " + completableProductResult.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(LocalDateTime.now() + ": Main: end");
    }

}
