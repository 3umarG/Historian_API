package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.SubscriptionCardResponseDto;
import com.example.historian_api.entities.cards.SubscriptionCard;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SubscriptionCardToSubscriptionCardResponseDtoMapper implements Function<SubscriptionCard, SubscriptionCardResponseDto> {
    @Override
    public SubscriptionCardResponseDto apply(SubscriptionCard subscriptionCard) {
        return SubscriptionCardResponseDto
                .builder()
                .cardId(subscriptionCard.getId())
                .value(subscriptionCard.getCardValue())
                .isValid(subscriptionCard.isValid())
                .isPaid(subscriptionCard.isPaid())
                .creationOn(subscriptionCard.getCreatedOn())
                .expiredOn(subscriptionCard.getExpiredOn())
                .build();
    }
}
