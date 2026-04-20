package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.example.demo.service.CryptoService;

@RestController
@RequestMapping("/")
public class CryptoController {

    private final CryptoService service;

    public CryptoController(CryptoService service) {
        this.service = service;
    }

    

    @PostMapping("/crypt")
    public Map<String, String> encrypt(@RequestBody Map<String, String> body) {
        return Map.of("encrypted", service.encrypt(body.get("text")));
    }

    @GetMapping("/decrypt")
    public Map<String, String> decrypt(@RequestParam String data) {
        return Map.of("decrypted", service.decrypt(data));
    }

    @PostMapping("/hash")
    public Map<String, String> hash(@RequestBody Map<String, String> body) {
        return Map.of("hash", service.sha256(body.get("text")));
    }
}