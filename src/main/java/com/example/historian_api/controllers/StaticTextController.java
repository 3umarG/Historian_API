package com.example.historian_api.controllers;
import com.example.historian_api.dtos.requests.UpdateStaticTextRequestDto;
import com.example.historian_api.dtos.responses.StaticTextResponseDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.static_text.StaticTextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.version}/statics")
@RequiredArgsConstructor
public class StaticTextController {

    private final StaticTextService service;
    private final ResponseFactory200 successFactory;


    @GetMapping("/aboutText")
    public ResponseEntity<?> getAboutText() {
       Object aboutText = service.getAboutText();
        return ResponseEntity.ok(successFactory.createResponse(aboutText));
    }

    @GetMapping("/privacyText")
    public ResponseEntity<?> getPrivacyText() {
        Object privacyText = service.getPrivacyText();
        return ResponseEntity.ok(successFactory.createResponse(privacyText));
    }
    @GetMapping("/technicalSupport")
    public ResponseEntity<?> getTechnicalSupport() {
        List<String> technicalSupportContactInfo = service.getTechnicalSupportContactInfo();
        return ResponseEntity.ok(successFactory.createResponse(technicalSupportContactInfo));
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllTexts() {
        StaticTextResponseDto responseDto = service.getAllStaticText();
        return ResponseEntity.ok(successFactory.createResponse(responseDto));
    }
    @PutMapping("/aboutText")
    public ResponseEntity<?> updateAboutText(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        Object response = service.updateAboutText(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PutMapping("/vodafoneCash")
    public ResponseEntity<?> updateVodafoneCash(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        Object response = service.updateVodafoneCashNumber(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }


    @PutMapping("/privacyText")
    public ResponseEntity<?> updatePrivacyText(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        Object response = service.updatePrivacyText(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PutMapping("/googlePlay")
    public ResponseEntity<?> updateGooglePlay(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        Object response = service.updateGooglePlayLink(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PutMapping("/appStore")
    public ResponseEntity<?> updateAppStore(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        Object response = service.updateAppStoreLink(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PostMapping("/technicalSupport")
    public ResponseEntity<?> updateTechnicalSupport(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        Object response = service.addTechnicalSupportNumber(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

}
