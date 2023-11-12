package com.example.historian_api.entities.cards;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions_cards")
public class SubscriptionCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardValue;
    private boolean isValid;
    private LocalDate expiredOn;
    private LocalDate createdOn;
    private boolean isPaid;
}
