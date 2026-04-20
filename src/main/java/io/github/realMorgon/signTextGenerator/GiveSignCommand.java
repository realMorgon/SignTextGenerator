package io.github.realMorgon.signTextGenerator;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;

@CommandAlias("give-sign")
public class GiveSignCommand extends BaseCommand {

    @Default
    @Syntax("<material> <color> <font> <text>")
    @CommandCompletion("@material @color @font <text>")
    @Description("Gives a sign with custom text")
    public void giveSign(Player player, String material, String color, String font , String text) throws IOException {

        if(player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
            player.sendMessage("You need to be in creative mode to use this command.");
            return;
        }

        Material signMaterial;
        NamedTextColor signColor;
        try {
            signMaterial = Material.valueOf(material.toUpperCase() + "_SIGN");
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid material: " + material);
            return;
        }
        try {
            signColor = NamedTextColor.NAMES.value(color.toLowerCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid color: " + color);
            return;
        }

        InputStream inputStream;
        try {
            inputStream = JavaPlugin.getPlugin(SignTextGenerator.class).getDataPath().normalize().resolve(font + ".json").toUri().toURL().openStream();
        }catch (Exception IOException) {
            player.sendMessage("Error generating text. Make sure the font exists.");
            throw IOException;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonLayout jsonLayout;
        try {
            jsonLayout = objectMapper.readValue(inputStream, JsonLayout.class);
        }catch (Exception IOException) {
            player.sendMessage("Error generating text. Make sure the font exists.");
            return;
        }
        int maxCharsPerSign;
        if (material.contains("hanging")) {
            maxCharsPerSign = jsonLayout.maxCharsPerHangingSign;
        }else{
            maxCharsPerSign = jsonLayout.maxCharsPerSign;
        }

        StringBuilder textBuilder = new StringBuilder(text);
        while (textBuilder.length() % maxCharsPerSign != 0) {
            textBuilder.append(" ");
        }
        text = textBuilder.toString();

        for (int i = 0; i < text.length() / maxCharsPerSign; i++) {
            String currentChars = text.substring(i * maxCharsPerSign, i * maxCharsPerSign + maxCharsPerSign);
            String [] signText = TextGeneration.generateText(currentChars, font);
            if (signText == null) {
                player.sendMessage("Error generating text. Make sure the font exists and contains the desired characters.");
                continue;
            }
            ItemStack sign = new ItemStack(signMaterial);
            BlockStateMeta meta = (BlockStateMeta) sign.getItemMeta();
            Sign signBlock = (Sign) meta.getBlockState();
            signBlock.getSide(Side.FRONT).line(0, Component.text(signText[0], signColor));
            signBlock.getSide(Side.FRONT).line(1, Component.text(signText[1], signColor));
            signBlock.getSide(Side.FRONT).line(2, Component.text(signText[2], signColor));
            signBlock.getSide(Side.FRONT).line(3, Component.text(signText[3], signColor));
            signBlock.getSide(Side.FRONT).setGlowingText(true);
            meta.setBlockState(signBlock);
            meta.displayName(Component.text()
                    .append(Component.text("§r" + text.substring(0, (i + 1) * maxCharsPerSign - maxCharsPerSign), signColor, TextDecoration.ITALIC))
                    .append(Component.text(currentChars, signColor, TextDecoration.BOLD, TextDecoration.ITALIC))
                    .append(Component.text(text.substring(i * maxCharsPerSign + maxCharsPerSign), signColor, TextDecoration.ITALIC))
                    .build());
            boolean success = sign.setItemMeta(meta);
            if (success) {
                player.getInventory().addItem(sign);
            }else {
                player.sendMessage("Error giving sign. Could not set item meta.");
            }
        }


    }
}
