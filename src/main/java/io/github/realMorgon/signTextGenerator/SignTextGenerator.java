package io.github.realMorgon.signTextGenerator;

import co.aikar.commands.PaperCommandManager;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public final class SignTextGenerator extends JavaPlugin {

    @Override
    public void onEnable() {

        List<JarEntry> fontEntries = new ArrayList<>();
        URL jarUrl = GiveSignCommand.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            File file = new File(jarUrl.toURI());
            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith("fonts/") && !entry.isDirectory()) {
                        fontEntries.add(entry);
                    }
                }
            }
        }catch (URISyntaxException | IOException exception){
            getLogger().severe("Failed to read font files from plugin jar. No fonts will be available for use. Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        File dataFolder = getDataFolder();
        boolean success = true;
        if (!dataFolder.exists()) {
            success = dataFolder.mkdirs();
        }
        if(!success){
            getLogger().severe("Failed to create data folder for SignTextGenerator plugin. This is a crucial part of the Plugin.");
            getLogger().severe("Due to this error the plugin will be disabled. Please fix the issue and restart the server.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        for(JarEntry font : fontEntries) {
            Path dataPath = getDataPath().normalize();
            Path fontPath = getDataPath().resolve(font.getName().substring("fonts/".length())).normalize();
            if(!fontPath.startsWith(dataPath)) {
                getLogger().warning("Font " + font.getName() + " is trying to be extracted outside of the plugin data folder. This is a security risk and the file will not be extracted.");
                getLogger().warning("This font will not be available for use");
                continue;
            }

            if(fontPath.getParent() != null && !Files.exists(fontPath.getParent())) {
                try {
                    Files.createDirectories(fontPath.getParent());
                }catch (IOException e){
                    getLogger().warning("Failed to create directories for font file: " + font.getName());
                    getLogger().warning("This font will not be available for use");
                    continue;
                }
            }

            if(!Files.exists(fontPath)) {
                try (InputStream inputStream = getResource(font.getName());
                    OutputStream outputStream = Files.newOutputStream(fontPath)) {

                    if (inputStream == null) {
                        getLogger().warning("Failed to extract font file: " + font.getName() + ". Stream is empty.");
                        getLogger().warning("This font will not be available for use");
                        continue;
                    }

                    inputStream.transferTo(outputStream);
                }catch (IOException e){
                    getLogger().warning("Failed to extract font file: " + font.getName() + ". Could not create stream.");
                    getLogger().warning("This font will not be available for use");
                }
            }
        }

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.getCommandCompletions().registerAsyncCompletion("material", context -> {
            Collection<String> materialCompletions = new ArrayList<>();
            for (Material material : Material.values()) {
                if (material.name().endsWith("_SIGN") && !material.name().contains("WALL")) {
                    String baseName = material.name().replace("_SIGN", "").toLowerCase();
                    materialCompletions.add(baseName);
                }
            }
            return materialCompletions;
        });

        manager.getCommandCompletions().registerAsyncCompletion("color", context -> {
            Collection<String> colorCompletions = new ArrayList<>();
            for (NamedTextColor color : NamedTextColor.NAMES.values() ) {
                colorCompletions.add(color.toString().toLowerCase());
            }
            return colorCompletions;
        });

        Collection<String> fontCompletions = new ArrayList<>();
        Path dataPath = getDataPath().normalize();
        try (Stream<Path> paths = Files.walk(dataPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .forEach(path -> {
                        String fileName = dataPath.relativize(path).toString().replace("\\", "/");
                        String fontName = fileName.replace(".json", "");
                        if (!fontName.isEmpty()) {
                            fontCompletions.add(fontName);
                        }
                    });
        } catch (IOException e) {
            getLogger().warning("Failed to read font files from data folder.");
            getLogger().warning("No fonts will be available for use. Plugin will be disabled. Please check permissions.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Successfully loaded font files from data folder. Available fonts: " + fontCompletions.toString().substring(1, fontCompletions.toString().length() - 1));

        manager.getCommandCompletions().registerAsyncCompletion("font", context -> fontCompletions);

        manager.registerCommand(new GiveSignCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
