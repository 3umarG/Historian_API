package com.example.historian_api.controllers;


import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.device_serial.DeviceSerialNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}/device")
@RequiredArgsConstructor
public class DeviceInfoController {

    private final ResponseFactory200 successFactory;
    private final DeviceSerialNumberService complaintsService;
    @GetMapping("/serialNumber")
    public ResponseEntity<?> getDeviceSerialNumber() {
        String  response = complaintsService.getDeviceSerialNumber();
        return ResponseEntity.ok(successFactory.createResponse(response));
    }
}
