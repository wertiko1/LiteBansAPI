package me.wertiko.elyPunishment;

import com.fasterxml.jackson.databind.ObjectMapper;
import litebans.api.Entry;
import org.slf4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class PunishmentEventSender {

    private final String endpoint;
    private final Logger logger;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PunishmentEventSender(String endpoint, Logger logger) {
        this.endpoint = endpoint;
        this.logger = logger;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void sendHttpRequest(Entry entry, String eventType, String action) {
        PunishmentEvent punishmentEvent = new PunishmentEvent(
                eventType, action,
                entry.getId(), entry.getType(), entry.getUuid(), entry.getIp(),
                entry.getReason(), entry.getExecutorUUID(), entry.getExecutorName(),
                entry.getRemovedByUUID(), entry.getRemovedByName(), entry.getRemovalReason(),
                entry.getDateStart(), entry.getDateEnd(), entry.getServerScope(), entry.getServerOrigin(),
                entry.isSilent(), entry.isIpban(), entry.isActive(), entry.getRandomID()
        );

        try {
            String jsonBody = objectMapper.writeValueAsString(punishmentEvent);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            CompletableFuture<Void> future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            logger.info("Request sent successfully: " + response.body());
                        } else {
                            logger.error("Failed to send request. Status code: " + response.statusCode());
                        }
                    });

            future.exceptionally(ex -> {
                logger.error("Error sending HTTP request", ex);
                return null;
            });

        } catch (Exception e) {
            logger.error("Error serializing punishment event to JSON", e);
        }
    }
}
