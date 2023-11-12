package com.example.historian_api.services.impl.cards;

import com.example.historian_api.dtos.responses.SubscriptionCardResponseDto;
import com.example.historian_api.entities.cards.SubscriptionCard;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.SubscriptionCardToSubscriptionCardResponseDtoMapper;
import com.example.historian_api.repositories.cards.SubscriptionCardRepository;
import com.example.historian_api.services.base.card.SubscriptionCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class SubscriptionCardServiceImpl implements SubscriptionCardService {

    private final SubscriptionCardRepository repository;
    private final SubscriptionCardToSubscriptionCardResponseDtoMapper mapper;
    @Override
    public SubscriptionCardResponseDto saveCard() {
        List<SubscriptionCard>subscriptionCards=repository.findAll();
        String cardValue=generateRandomString();
        if(cardValue.isEmpty()){
            throw new NotFoundResourceException("Something went wrong");
        }
        for (SubscriptionCard card : subscriptionCards) {
            if (!card.getCardValue().equals(cardValue)) {
                break;
            }
            cardValue = generateRandomString();
        }
        SubscriptionCard card=SubscriptionCard
                .builder()
                .cardValue(cardValue)
                .isValid(true)
                .createdOn(LocalDate.now())
                .expiredOn(null)
                .isPaid(false)
                .build();

        repository.save(card);
        return mapper.apply(card);
    }

    @Override
    public List<SubscriptionCardResponseDto> getCards() {
        List<SubscriptionCard>subscriptionCards=repository.findAll();
        return subscriptionCards.stream().map(subscriptionCard -> mapper.apply(subscriptionCard)).toList();
    }

    public static String generateRandomString() {
        StringBuilder stringBuilder = new StringBuilder(14);
        for (int i = 0; i < 14; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
            stringBuilder.append(randomNum);
        }
        return stringBuilder.toString();
    }
}
