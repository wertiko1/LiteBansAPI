package me.wertiko.elyPunishment;

import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import litebans.api.Entry;
import litebans.api.Events;
import lombok.Getter;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin(id = "elypunishment", name = "ElyPunishment", version = BuildConstants.VERSION)
public class ElyPunishment {

    private String endpoint;
    private YamlDocument config;

    private final PunishmentEventSender punishmentEventSender;
    public ConfigHandler configHandler;

    @Getter
    private final Logger logger;
    @Getter
    private final ProxyServer proxy;

    @Inject
    public ElyPunishment(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;

        configHandler = new ConfigHandler(this);
        configHandler.initConfig(dataDirectory);
        this.config = configHandler.getConfig();

        this.endpoint = config.getString("ENDPOINT", null);
        if (endpoint == null) {
            logger.error("Server URL is missing in the configuration!");
        }

        punishmentEventSender = new PunishmentEventSender(endpoint, logger);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("ElyPunishment starting...");
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                String eventType = entry.getType();
                punishmentEventSender.sendHttpRequest(entry, eventType, "added");
            }

            @Override
            public void entryRemoved(Entry entry) {
                String eventType = entry.getType();
                punishmentEventSender.sendHttpRequest(entry, eventType, "removed");
            }
        });
    }
}
