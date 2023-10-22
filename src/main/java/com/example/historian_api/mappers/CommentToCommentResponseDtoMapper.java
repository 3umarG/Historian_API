package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.entities.posts.Comment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class CommentToCommentResponseDtoMapper implements Function<Comment, CommentResponseDto> {
    @Override
    public CommentResponseDto apply(Comment comment) {
        var createdOn = comment.getCreationDate();
        var createdSince = calculateCreatedSinceForComment(createdOn);
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                createdOn,
                createdSince,
                comment.getCreator().getId(),
                comment.getCreator().getPhotoUrl(),
                comment.getCreator().getName(),
                comment.getPost().getId()
        );
    }

    private String calculateCreatedSinceForComment(LocalDateTime creationDate) {
        LocalDateTime dateNow = LocalDateTime.now();
        Duration duration = Duration.between(creationDate, dateNow);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        String createdSince;

        if (days >= 365) {
            createdSince = calculateCreatedSinceYears(days);
        } else if (days >= 30) {
            createdSince = calculateCreatedSinceMonths(days);
        } else if (days > 0) {
            createdSince = calculateCreatedSinceDays(days);
        } else if (hours > 0) {
            createdSince = calculateCreatedSinceHours(hours);
        } else {
            createdSince = calculateCreatedSinceMinutes(minutes);
        }
        return createdSince;
    }

    private static String calculateCreatedSinceYears(long days) {
        String createdSince;
        long years = days / 365;
        if (years == 2) {
            createdSince = "منذ سنتين";
        } else if (years == 1) {
            createdSince = "منذ سنة";
        } else if (years <= 10) {
            createdSince = "منذ " + years + " سنوات";
        } else {
            createdSince = "منذ " + years + " سنة";
        }
        return createdSince;
    }

    private static String calculateCreatedSinceMonths(long days) {
        String createdSince;
        long months = days / 30;
        if (months == 2) {
            createdSince = "منذ شهرين";
        } else if (months == 1) {
            createdSince = "منذ شهر";
        } else if (months <= 10) {
            createdSince = "منذ " + months + " أشهر";
        } else {
            createdSince = "منذ " + months + " شهر";
        }
        return createdSince;

    }

    private static String calculateCreatedSinceDays(long days) {
        String createdSince;
        if (days == 2) {
            createdSince = "منذ يومين";
        } else if (days == 1) {
            createdSince = "منذ يوم";
        } else if (days <= 10) {
            createdSince = "منذ " + days + " أيام";
        } else {
            createdSince = "منذ " + days + " يوم";
        }
        return createdSince;
    }

    private static String calculateCreatedSinceHours(long hours) {
        String createdSince;
        if (hours == 2) {
            createdSince = "منذ ساعتين";
        } else if (hours == 1) {
            createdSince = "منذ ساعة";
        } else if (hours <= 10) {
            createdSince = "منذ " + hours + " ساعات";
        } else {
            createdSince = "منذ " + hours + " ساعة";
        }
        return createdSince;
    }

    private static String calculateCreatedSinceMinutes(long minutes) {
        String createdSince;
        if (minutes == 1) {
            createdSince = "منذ دقيقة";
        } else if (minutes == 2) {
            createdSince = "منذ دقيقتين";
        } else if (minutes <= 10) {
            createdSince = "منذ " + minutes + " دقائق";
        } else {
            createdSince = "منذ " + minutes + " دقيقة";
        }

        return createdSince;
    }
}
