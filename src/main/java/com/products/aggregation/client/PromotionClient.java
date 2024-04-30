package com.products.aggregation.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.products.aggregation.dto.Promotion;

import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PromotionClient {


    private final WebClient client;
    private final Promotion noPromotion = new Promotion("no-promotion", 0.0, LocalDate.of(2999, 12, 31));

    public PromotionClient(WebClient.Builder builder, @Value("${app.client.promotion-url}") String promotionUri) {
        this.client = builder.baseUrl(promotionUri).build();
    }

    public Mono<Promotion> getPromotion(Integer productId){
        return this.client
                .get()
                .uri("{productId}", productId)
                .retrieve()
                .bodyToMono(Promotion.class)
                .onErrorReturn(noPromotion); // if no result, it returns 404, so switch to no promotion
    }

}
