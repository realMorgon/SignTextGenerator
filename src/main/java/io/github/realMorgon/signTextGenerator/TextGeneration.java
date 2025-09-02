package io.github.realMorgon.signTextGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class TextGeneration {

    public static String[] generateText(String text, String font) {
        String[] lines = new String[4];
        InputStream inputStream;

        try {
            inputStream = SignTextGenerator.getPlugin().getResource("fonts/" + font + ".json");
        }catch (Exception IOException) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonLayout jsonLayout;
        try {
            jsonLayout = objectMapper.readValue(inputStream, JsonLayout.class);
        }catch (Exception IOException) {
            return null;
        }
        HashMap<Character, String[]> charMap = jsonLayout.charMap;

        for (int i = 0; i < text.length(); i++) {
            String[] charLines;
            try {
                char c = text.charAt(i);
                charLines = charMap.get(c);
            }catch (Exception IOException) {
                return null;
            }
            for (int j = 0; j < 4; j++) {
                if (lines[j] == null) {
                    lines[j] = charLines[j];
                }else {
                    lines[j] += " " + charLines[j];
                }
            }
        }
        return lines;
    }

}

class JsonLayout {
    public int maxCharsPerSign;
    public HashMap<Character, String[]> charMap;
}
