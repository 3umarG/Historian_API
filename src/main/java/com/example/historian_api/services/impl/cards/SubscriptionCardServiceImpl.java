package com.example.historian_api.services.impl.cards;

import com.example.historian_api.dtos.responses.SubscriptionCardResponseDto;
import com.example.historian_api.entities.cards.SubscriptionCard;
import com.example.historian_api.mappers.SubscriptionCardToSubscriptionCardResponseDtoMapper;
import com.example.historian_api.repositories.cards.SubscriptionCardRepository;
import com.example.historian_api.services.base.card.SubscriptionCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class SubscriptionCardServiceImpl implements SubscriptionCardService {

    private final SubscriptionCardRepository repository;
    private final SubscriptionCardToSubscriptionCardResponseDtoMapper mapper;

    @Override
    public SubscriptionCardResponseDto saveCard() {
        SubscriptionCard card = generateUniqueCard();
        repository.save(card);
        return mapper.apply(card);
    }

    @Override
    public List<SubscriptionCardResponseDto> getCards() {
        List<SubscriptionCard> subscriptionCards = repository.findAll();
        return subscriptionCards.stream().map(mapper::apply).toList();
    }

    @Override
    public List<SubscriptionCardResponseDto> generateManyCards(Integer count) {
        List<SubscriptionCardResponseDto> cardResponseDtoList = new ArrayList<>();
        List<SubscriptionCard> cards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SubscriptionCard card = generateUniqueCard();
            cards.add(card);
            cardResponseDtoList.add(mapper.apply(card));
        }
        repository.saveAll(cards);
        return cardResponseDtoList;
    }

    private SubscriptionCard generateUniqueCard() {
        String cardValue = generateUniqueCardValue();
        return SubscriptionCard.builder()
                .cardValue(cardValue)
                .isValid(true)
                .createdOn(LocalDate.now())
                .expiredOn(null)
                .isPaid(false)
                .build();
    }

    private String generateUniqueCardValue() {
        List<SubscriptionCard> existingCards = repository.findAll();
        String cardValue = generateRandomString();
        for (SubscriptionCard card : existingCards) {
            if (!card.getCardValue().equals(cardValue)) {
                break;
            }
            cardValue = generateRandomString();
        }
        return cardValue;
    }

    private static String generateRandomString() {
        StringBuilder stringBuilder = new StringBuilder(14);
        for (int i = 0; i < 14; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
            stringBuilder.append(randomNum);
        }
        return stringBuilder.toString();
    }
}
