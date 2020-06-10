package com.homurax.searchWithoutIndexing.concurrent.main;

import com.homurax.searchWithoutIndexing.data.Product;
import com.homurax.searchWithoutIndexing.data.ProductRecommendation;
import com.homurax.searchWithoutIndexing.data.ProductReview;
import com.homurax.searchWithoutIndexing.data.Review;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class ConcurrentMainRecomendation {

    public static void main(String[] args) {
        String user = "A2JOYUS36FLG4Z";
        Path file = Paths.get("data");
        try {
            LocalDateTime start = LocalDateTime.now();

            List<Product> productList = Files
                    .walk(file, FileVisitOption.FOLLOW_LINKS)
                    .parallel()
                    .filter(f -> f.toString().endsWith(".txt"))
                    .collect(ArrayList::new, new ConcurrentLoaderAccumulator(), List::addAll);

            Map<String, List<ProductReview>> productsByBuyer = productList
                    .stream()
                    .unordered()
                    .parallel()
                    .flatMap(p -> p.getReviews().stream().map(r -> new ProductReview(p, r.getUser(), r.getValue())))
                    .collect(Collectors.groupingByConcurrent(ProductReview::getBuyer));

            Map<String, List<ProductReview>> recommendedProducts = productsByBuyer.get(user)
                    .parallelStream()
                    .map(Product::getReviews)
                    .flatMap(Collection::stream)
                    .map(Review::getUser)
                    .distinct()
                    .map(productsByBuyer::get)
                    .flatMap(Collection::stream)
                    .collect(Collectors.groupingByConcurrent(Product::getTitle));


            ConcurrentLinkedDeque<ProductRecommendation> recommendations = recommendedProducts
                    .entrySet()
                    .parallelStream()
                    .map(entry -> new ProductRecommendation(
                            entry.getKey(),
                            entry.getValue().stream().mapToInt(ProductReview::getValue).average().getAsDouble()))
                    .sorted()
                    .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

            LocalDateTime end = LocalDateTime.now();

            recommendations.forEach(pr -> System.out.println(pr.getTitle() + ": " + pr.getValue()));

            System.out.println("Execution Time: " + Duration.between(start, end));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
