package io.github.realMorgon.signTextGenerator;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignTextGenerator extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TextGeneration.initCharMap();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new GiveSignCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
