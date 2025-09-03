package io.github.realMorgon.signTextGenerator;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@CommandAlias("givesign")
public class GiveSignCommand extends BaseCommand {

    @Default
    @Syntax("<material> <color> <font> <text>")
    @CommandCompletion("@material @color @font")
    public void giveSign(String material, String color, String font , String text, Player player) {

        if(player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
            player.sendMessage("You need to be in creative mode to use this command.");
            return;
        }

        Material signMaterial;
        ChatColor signColor;
        try {
            signMaterial = Material.valueOf(material.toUpperCase() + "_SIGN");
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid material: " + material);
            return;
        }
        try {
            signColor = ChatColor.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid color: " + text);
            return;
        }

        InputStream inputStream;
        try {
            inputStream = SignTextGenerator.getPlugin().getResource("fonts/" + font + ".json");
        }catch (Exception IOException) {
            player.sendMessage("Error generating text. Make sure the font exists.");
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonLayout jsonLayout;
        try {
            jsonLayout = objectMapper.readValue(inputStream, JsonLayout.class);
        }catch (Exception IOException) {
            player.sendMessage("Error generating text. Make sure the font exists.");
            return;
        }
        int maxCharsPerSign = jsonLayout.maxCharsPerSign;
        if (material.contains("hanging")) {
            maxCharsPerSign = Math.round((float) maxCharsPerSign / 2);
        }

        while (text.length() % maxCharsPerSign != 0) {
            text += " ";
        }

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
            signBlock.setLine(0, signColor + signText[0]);
            signBlock.setLine(1, signColor + signText[1]);
            signBlock.setLine(2, signColor + signText[2]);
            signBlock.setLine(3, signColor + signText[3]);
            signBlock.setGlowingText(true);
            meta.setBlockState(signBlock);
            meta.setDisplayName(signColor + "§r" + text.substring(0, (i + 1) * maxCharsPerSign - maxCharsPerSign) + ChatColor.BOLD + currentChars + ChatColor.RESET + text.substring(i * maxCharsPerSign + maxCharsPerSign));
            sign.setItemMeta(meta);
            player.getInventory().addItem(sign);
        }


    }
}
