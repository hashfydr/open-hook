package com.hash.openhook;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "*") // Allow all origins for the MVP
public class WebhookController {

    @Autowired
    private WebhookRepository repository;

    @Autowired
    private SseService sseService;

    @RequestMapping(value = "/{urlId}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
    public ResponseEntity<String> handleWebhook(
            @PathVariable String urlId,
            HttpServletRequest request,
            @RequestHeader HttpHeaders headers,
            @RequestBody(required = false) String body) {

        // Format headers as a simple JSON string or mapped string
        StringBuilder headersBuilder = new StringBuilder("{");
        headers.forEach((key, value) -> {
            headersBuilder.append("\"").append(key).append("\":\"")
                    .append(String.join(",", value)).append("\",");
        });
        if (headersBuilder.length() > 1) {
            headersBuilder.setLength(headersBuilder.length() - 1); // remove last comma
        }
        headersBuilder.append("}");

        // Create Entity
        WebhookRequest webhookReq = new WebhookRequest(urlId, request.getMethod(), headersBuilder.toString(), body == null ? "" : body);
        
        // Save to DB
        WebhookRequest savedReq = repository.save(webhookReq);

        // Push to any listening frontends
        sseService.emit(urlId, savedReq);

        return ResponseEntity.ok("{\"status\": \"success\"}");
    }
}
