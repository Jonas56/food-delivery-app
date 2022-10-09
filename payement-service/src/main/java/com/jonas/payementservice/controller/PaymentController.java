package com.jonas.payementservice.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/checkout")
public class PaymentController {

    @Value("{stripe.apiKey}")
    private String StripeApiKey;

    @PostMapping()
    public Session checkout() throws StripeException {
        Stripe.apiKey = StripeApiKey;

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                        .setPrice("{{PRICE_ID}}")
                                        .build())
                        .build();
       String test =  "https://blog.devgenius.io/paypal-integration-with-spring-boot-f1e297d76336";
        return Session.create(params);
    }

}


