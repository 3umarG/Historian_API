package com.example.historian_api.controllers;
import com.example.historian_api.dtos.responses.SubscriptionCardResponseDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.card.SubscriptionCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/cards")
@RequiredArgsConstructor
public class SubscriptionCardController {

    private final SubscriptionCardService service;
    private final ResponseFactory200 successFactory;
    @GetMapping()
    public ResponseEntity<?> getSubscriptionCards() {
        List<SubscriptionCardResponseDto> cards = service.getCards();
        return ResponseEntity.ok(successFactory.createResponse(cards));
    }

    @PostMapping
    public ResponseEntity<?> saveSubscriptionCards() {
        SubscriptionCardResponseDto response = service.saveCard();
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PostMapping("/{counter}")
    public ResponseEntity<?> saveManySubscriptionCards(@PathVariable Integer counter) {
        List<SubscriptionCardResponseDto> response = service.generateManyCards(counter);
        return ResponseEntity.ok(successFactory.createResponse(response));
    }
}
