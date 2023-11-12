package com.example.historian_api.repositories.cards;

import com.example.historian_api.entities.cards.SubscriptionCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubscriptionCardRepository extends JpaRepository<SubscriptionCard, Long> {

    @Query(value = "select case when exists( " +
                   "    select cards.id " +
                   "    from subscriptions_cards cards " +
                   "    where cards.card_value = ?1 AND cards.is_valid = true " +
                   ") " +
                   "then cast(1 as bit) " +
                   "else cast(0 as bit) end", nativeQuery = true)
    boolean isActiveCardCode(String code);

    @Modifying
    @Transactional
    @Query(value = "UPDATE subscriptions_cards " +
                   "SET is_valid = false, " +
                   "expired_on = CURRENT_DATE " +
                   "WHERE card_value = ?1",
            nativeQuery = true)
    void invalidateCardByCode(String code);
}
