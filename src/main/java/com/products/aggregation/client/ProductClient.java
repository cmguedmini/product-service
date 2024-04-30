package com.products.aggregation.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.products.aggregation.dto.Product;

import reactor.core.publisher.Mono;

@Service
public class ProductClient {
	


	private final WebClient client;

	public ProductClient(WebClient.Builder builder, @Value("${app.client.product-url}") String productUri) {
		this.client = builder.baseUrl(productUri).build();
	}

	public Mono<Product> getProduct(Integer productId) {
		return this.client.get().uri("{productId}", productId).retrieve().bodyToMono(Product.class)
				.onErrorResume(ex -> Mono.empty()); // switch it to empty in case of error
	}

}
