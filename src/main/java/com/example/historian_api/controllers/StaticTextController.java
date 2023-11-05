package com.example.historian_api.controllers;
import com.example.historian_api.dtos.requests.UpdateStaticTextRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.static_text.StaticTextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.version}")
@RequiredArgsConstructor
public class StaticTextController {

    private final StaticTextService service;
    private final ResponseFactory200 successFactory;


    @GetMapping("/aboutText")
    public ResponseEntity<?> getAboutText() {
       String aboutText = service.getAboutText();
        return ResponseEntity.ok(successFactory.createResponse(aboutText));
    }

    @GetMapping("/privacyText")
    public ResponseEntity<?> getPrivacyText() {
        String privacyText = service.getPrivacyText();
        return ResponseEntity.ok(successFactory.createResponse(privacyText));
    }
    @GetMapping("/technicalSupport")
    public ResponseEntity<?> getTechnicalSupport() {
        String technicalSupportContactInfo = service.getTechnicalSupportContactInfo();
        return ResponseEntity.ok(successFactory.createResponse(technicalSupportContactInfo));
    }
    @PutMapping("/aboutText")
    public ResponseEntity<?> updateAboutText(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
       String response = service.updateAboutText(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PutMapping("/privacyText")
    public ResponseEntity<?> updatePrivacyText(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        String response = service.updatePrivacyText(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }
    @PutMapping("/technicalSupport")
    public ResponseEntity<?> updateTechnicalSupport(@Valid @RequestBody UpdateStaticTextRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        String response = service.updateTechnicalSupportContactInfo(dto.newText());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

}
