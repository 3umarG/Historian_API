package com.example.historian_api.services.base.card;

import com.example.historian_api.dtos.responses.SubscriptionCardResponseDto;

import java.util.List;

public interface SubscriptionCardService {
    SubscriptionCardResponseDto saveCard();
    List<SubscriptionCardResponseDto>getCards();
}
