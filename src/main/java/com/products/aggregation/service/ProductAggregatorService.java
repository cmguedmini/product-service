package com.products.aggregation.service;

import com.products.aggregation.client.ProductClient;
import com.products.aggregation.client.PromotionClient;
import com.products.aggregation.client.ReviewClient;
import com.products.aggregation.dto.Product;
import com.products.aggregation.dto.ProductAggregate;
import com.products.aggregation.dto.Promotion;
import com.products.aggregation.dto.Review;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductAggregatorService {

    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> getProduct(Integer productId){
        return Mono.zip(
                this.productClient.getProduct(productId),
                this.promotionClient.getPromotion(productId),
                this.reviewClient.getReviews(productId)
        )
        .map(this::combine);
    }

    private ProductAggregate combine(Tuple3<Product, Promotion, List<Review>> tuple){
        return ProductAggregate.create(
                tuple.getT1(),
                tuple.getT2(),
                tuple.getT3()
        );
    }

}
