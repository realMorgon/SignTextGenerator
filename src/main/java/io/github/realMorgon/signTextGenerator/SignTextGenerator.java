package io.github.realMorgon.signTextGenerator;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class SignTextGenerator extends JavaPlugin {

    private static SignTextGenerator plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
//        TextGeneration.initCharMap();

        plugin = this;

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.getCommandCompletions().registerAsyncCompletion("material", c -> {
            Collection<String> materialCompletions = new ArrayList<>();
            for (Material material : Material.values()) {
                if (material.name().endsWith("_SIGN") && !material.name().contains("WALL")) {
                    String baseName = material.name().replace("_SIGN", "").toLowerCase();
                    materialCompletions.add(baseName);
                }
            }
            return materialCompletions;
        });

        manager.getCommandCompletions().registerAsyncCompletion("color", c -> {
            Collection<String> colorCompletions = new ArrayList<>();
            for (ChatColor color : ChatColor.values()) {
                if (color.isColor()) {
                    colorCompletions.add(color.name().toLowerCase());
                }
            }
            return colorCompletions;
        });

        manager.getCommandCompletions().registerAsyncCompletion("font", c -> {
            List<String> fileNames = new ArrayList<>();
            URL jarUrl = GiveSignCommand.class.getProtectionDomain().getCodeSource().getLocation();
            try {
                File jarFile = new File(jarUrl.toURI());
                try (JarFile jar = new JarFile(jarFile)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().startsWith("fonts/") && !entry.isDirectory()) {
                            fileNames.add(entry.getName().substring("fonts/".length()));
                        }
                    }
                }
            }catch (Exception e){}

            Collection<String> fontCompletions = new ArrayList<>();
            for (String fileName : fileNames) {
                String fontName = fileName.replace(".json", "");
                fontCompletions.add(fontName);
            }

            return  fontCompletions;
        });

        manager.registerCommand(new GiveSignCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SignTextGenerator getPlugin() {
        return plugin;
    }

}
