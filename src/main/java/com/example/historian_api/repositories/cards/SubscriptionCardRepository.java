package com.example.historian_api.repositories.cards;

import com.example.historian_api.entities.cards.SubscriptionCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionCardRepository extends JpaRepository<SubscriptionCard,Long> {
}
