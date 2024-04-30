package com.products.aggregation.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.products.aggregation.dto.Review;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {
	
    private final WebClient client;

    public ReviewClient(WebClient.Builder builder, @Value("${app.client.review-url}") String reviewUri) {
        this.client = builder.baseUrl(reviewUri).build();
    }

    public Mono<List<Review>> getReviews(Integer productId){
        return this.client
                .get()
                .uri(b -> b.queryParam("productId", productId).build())
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .onErrorReturn(Collections.emptyList()); // in case of error, switch it to empty list
    }

}
