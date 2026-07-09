package com.hash.openhook;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "webhook_requests")
public class WebhookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String urlId;

    @Column(nullable = false, length = 10)
    private String method;

    @Column(columnDefinition = "TEXT")
    private String headers;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public WebhookRequest() {
        this.timestamp = LocalDateTime.now();
    }

    public WebhookRequest(String urlId, String method, String headers, String body) {
        this.urlId = urlId;
        this.method = method;
        this.headers = headers;
        this.body = body;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getUrlId() { return urlId; }
    public void setUrlId(String urlId) { this.urlId = urlId; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getHeaders() { return headers; }
    public void setHeaders(String headers) { this.headers = headers; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
