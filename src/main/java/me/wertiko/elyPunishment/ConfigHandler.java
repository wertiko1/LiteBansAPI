package me.wertiko.elyPunishment;

import dev.dejvokep.boostedyaml.YamlDocument;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class ConfigHandler {
    @Getter
    private final ElyPunishment plugin;
    @Getter
    private YamlDocument config;

    public ConfigHandler(ElyPunishment plugin) {
        this.plugin = plugin;
    }

    public void initConfig(@DataDirectory Path dataDirectory) {
        try {
            config = YamlDocument.create(new File(dataDirectory.toFile(), "config.yml"), Objects.requireNonNull(getClass().getResourceAsStream("/config.yml")));
            config.update();
            config.save();
        } catch (IOException e) {
            plugin.getLogger().error("Config initialize error. ", e);
        }
    }
}
