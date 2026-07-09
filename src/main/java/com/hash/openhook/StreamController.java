package com.hash.openhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow all origins for MVP
public class StreamController {

    @Autowired
    private SseService sseService;

    @Autowired
    private WebhookRepository repository;

    // Stream new events
    @GetMapping("/stream/{urlId}")
    public SseEmitter streamEvents(@PathVariable String urlId) {
        return sseService.subscribe(urlId);
    }

    // Fetch historical events
    @GetMapping("/requests/{urlId}")
    public List<WebhookRequest> getHistory(@PathVariable String urlId) {
        return repository.findByUrlIdOrderByTimestampDesc(urlId);
    }
}
