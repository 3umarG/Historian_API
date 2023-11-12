package com.example.historian_api.dtos.responses;

import com.example.historian_api.entities.shared.ImageData;

public record ImageDataWithTitleDto(ImageData imageData, String photoUrl) {
}
