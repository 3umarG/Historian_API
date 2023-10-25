package com.example.historian_api.services.base.helpers;

import java.time.LocalDateTime;

public interface TimeSinceFormatter {
    String formatTimeSince(LocalDateTime creationDate);
}
