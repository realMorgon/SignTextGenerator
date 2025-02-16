package io.github.realMorgon.signTextGenerator;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

@CommandAlias("givesign")
public class GiveSignCommand extends BaseCommand {

    @Default
    @Syntax("<material> <color> <text>")
    @CommandCompletion("oak|spruce|birch|jungle|acacia|dark_oak|mangrove|cherry|bamboo|crimson|warped black|dark_blue|dark_green|dark_aqua|dark_red|dark_purple|gold|gray|dark_gray|blue|green|aqua|red|light_purple|yellow|white <text>")
    public void giveSign(String material, String color , String text, Player player) {

        Material signMaterial;
        ChatColor signColor;
        try {
            signMaterial = Material.valueOf(material.toUpperCase() + "_SIGN");
        } catch (IllegalArgumentException e) {
            player.sendMessage("Ungültiges Material: " + material);
            return;
        }
        try {
            signColor = ChatColor.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("Ungültige Farbe: " + text);
            return;
        }

        if (text.length() % 2 != 0) {
            text += " ";
        }

        for (int i = 0; i < text.length() / 2; i++) {
            String [] signText = TextGeneration.generateText(text.substring(i * 2, i * 2 + 2));
            ItemStack sign = new ItemStack(signMaterial);
            BlockStateMeta meta = (BlockStateMeta) sign.getItemMeta();
            Sign signBlock = (Sign) meta.getBlockState();
            signBlock.setLine(0, signColor + signText[0]);
            signBlock.setLine(1, signColor + signText[1]);
            signBlock.setLine(2, signColor + signText[2]);
            signBlock.setLine(3, signColor + signText[3]);
            signBlock.setGlowingText(true);
            meta.setBlockState(signBlock);
            meta.setDisplayName(signColor + "§r" + text.substring(0, (i + 1) * 2 - 2) + text.substring(i * 2, i * 2 + 2).toUpperCase() + text.substring(i * 2 + 2));
            sign.setItemMeta(meta);
            player.getInventory().addItem(sign);
        }


    }
}
