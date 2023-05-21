package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.Configurations.PaymentService;
import com.example.bookstore_back.DataAccessObjects.PaymentRequest;
import com.example.bookstore_back.DataAccessObjects.PaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(
            PaymentService paymentService
    ){
        this.paymentService = paymentService;
    }

    @ResponseBody
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(request.getAmount(), "KZT");
            return new PaymentResponse(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            e.printStackTrace();
            return new PaymentResponse(null);
        }
    }
}
