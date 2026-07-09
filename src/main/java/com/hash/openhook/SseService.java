package com.hash.openhook;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    private final ConcurrentHashMap<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String urlId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Keep alive forever
        emitters.computeIfAbsent(urlId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(urlId, emitter));
        emitter.onTimeout(() -> removeEmitter(urlId, emitter));
        emitter.onError((e) -> removeEmitter(urlId, emitter));

        return emitter;
    }

    private void removeEmitter(String urlId, SseEmitter emitter) {
        List<SseEmitter> urlEmitters = emitters.get(urlId);
        if (urlEmitters != null) {
            urlEmitters.remove(emitter);
        }
    }

    public void emit(String urlId, WebhookRequest request) {
        List<SseEmitter> urlEmitters = emitters.get(urlId);
        if (urlEmitters != null) {
            for (SseEmitter emitter : urlEmitters) {
                try {
                    emitter.send(SseEmitter.event().name("webhook").data(request));
                } catch (IOException e) {
                    emitter.complete();
                    removeEmitter(urlId, emitter);
                }
            }
        }
    }
}
