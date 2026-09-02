package com.metesevim.staj2026;

import com.metesevim.staj2026.repository.ProductSearchRepository;
import com.metesevim.staj2026.repository.CouchbaseProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class Staj2026ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoBean
    private ProductSearchRepository productSearchRepository;

    @MockitoBean
    private CouchbaseProductRepository couchbaseProductRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void liquibaseMigrationsAreApplied() {
        Integer changeCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM DATABASECHANGELOG",
                Integer.class
        );

        assertEquals(2, changeCount);
    }

    @Test
    void websocketBroadcastsMessages() throws Exception {
        CompletableFuture<String> receivedMessage = new CompletableFuture<>();
        StandardWebSocketClient client = new StandardWebSocketClient();

        WebSocketSession session = client.execute(
                new TextWebSocketHandler() {
                    @Override
                    protected void handleTextMessage(
                            WebSocketSession session,
                            TextMessage message
                    ) {
                        receivedMessage.complete(message.getPayload());
                    }
                },
                "ws://localhost:" + port + "/ws/messages"
        ).get(5, TimeUnit.SECONDS);

        try {
            session.sendMessage(new TextMessage("Merhaba WebSocket"));
            String response = receivedMessage.get(5, TimeUnit.SECONDS);

            assertTrue(response.contains("\"message\":\"Merhaba WebSocket\""));
        } finally {
            session.close();
        }
    }
}
