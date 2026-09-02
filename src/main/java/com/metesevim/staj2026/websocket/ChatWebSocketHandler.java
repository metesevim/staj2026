package com.metesevim.staj2026.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession sender, TextMessage message) throws IOException {
        String content = message.getPayload().trim();
        if (content.isEmpty() || content.length() > 300) {
            return;
        }

        String response = objectMapper.writeValueAsString(Map.of(
                "message", content,
                "sender", sender.getId().substring(0, Math.min(8, sender.getId().length())),
                "sentAt", Instant.now().toString()
        ));

        for (WebSocketSession session : sessions) {
            sendIfOpen(session, response);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    private void sendIfOpen(WebSocketSession session, String payload) throws IOException {
        if (!session.isOpen()) {
            sessions.remove(session);
            return;
        }

        synchronized (session) {
            session.sendMessage(new TextMessage(payload));
        }
    }
}
