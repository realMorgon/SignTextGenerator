package io.github.realMorgon.signTextGenerator;

import java.util.HashMap;

public class TextGeneration {

    public static String[] generateText(String inputText) {
        String text = inputText.toLowerCase();
        String[] lines = new String[4];
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!charMap.containsKey(c)) {
                System.out.println("Character not found: " + c);
                return null;
            }else {
                String[] charLines = charMap.get(c);
                for (int j = 0; j < 4; j++) {
                    if (lines[j] == null) {
                        lines[j] = charLines[j];
                    }else {
                        lines[j] += " " + charLines[j];
                    }
                }
            }
        }
        return lines;
    }

    static HashMap<Character, String[]> charMap = new HashMap<>(); // HashMap to store the characters and their representation on a sign
    public static void initCharMap() {
        charMap.put(' ', new String[] {"        ", "        ", "        ", "        "});
        charMap.put('a', new String[] {"████","█    █", "████", "█    █"});
        charMap.put('b', new String[] {"███  ", "█▄▄█", "█▀▀█", "███  "});
        charMap.put('c', new String[] {"████", "█      ", "█      ", "████"});
        charMap.put('d', new String[] {"███  ", "█    █", "█    █", "███  "});
        charMap.put('e', new String[] {"████", "█▄▄▄", "█▀▀▀", "████"});
        charMap.put('f', new String[] {"████", "█▄▄▄", "█▀▀▀", "█    "});
        charMap.put('g', new String[] {"████", "█    ", "█  ██", "████"});
        charMap.put('h', new String[] {"█   █", "█   █", "████", "█   █"});
        charMap.put('i', new String[] {"████", "  █  ", "  █  ", "████"});
        charMap.put('j', new String[] {"████", "  █  ", "  █  ", "██  "});
        charMap.put('k', new String[] {"█   █", "█ █  ", "█  █ ", "█   █"});
        charMap.put('l', new String[] {"█    ", "█    ", "█    ", "████"});
        charMap.put('m', new String[] {"█   █", "██ ██", "█ █ █", "█   █"});
        charMap.put('n', new String[] {"█   █", "██  █", "█ █ █", "█  ██"});
        charMap.put('o', new String[] {"████", "█   █", "█   █", "████"});
        charMap.put('p', new String[] {"████", "█   █", "████", "█    "});
        charMap.put('q', new String[] {"████", "█     █", "█  █  ", "  █  █"});
        charMap.put('r', new String[] {"████", "█   █", "████", "█   █"});
        charMap.put('s', new String[] {"████", "█    ", "████", "    █"});
        charMap.put('t', new String[] {"████", "  █  ", "  █  ", "  █  "});
        charMap.put('u', new String[] {"█   █", "█   █", "█   █", "████"});
        charMap.put('v', new String[] {"█   █", "█   █", "█   █", " █ █ "});
        charMap.put('w', new String[] {"█   █", "█ █ █", "█ █ █", " █ █ "});
        charMap.put('x', new String[] {"█   █", " █ █ ", " █ █ ", "█   █"});
        charMap.put('y', new String[] {"█   █", " █ █ ", "  █  ", "  █  "});
        charMap.put('z', new String[] {"████", "   █ ", "  █  ", "████"});
        charMap.put('0', new String[] {"██", "█     █", "█     █", "██"});
        charMap.put('1', new String[] {"██ ", "█   █   ", "  █", "████"});
        charMap.put('2', new String[] {"██", "▀  ▄█", "█▀     ", "████"});
        charMap.put('3', new String[] {"██", "▄▄▄█", "▀▀▀█", "███  "});
        charMap.put('4', new String[] {"█     █", "█     █", "████", "       █"});
        charMap.put('5', new String[] {"████", "█▄▄▄", "▀▀▀█", "███  "});
        charMap.put('6', new String[] {"███  ", "█▄▄▄", "█▀▀█", "███  "});
        charMap.put('7', new String[] {"████", "▀   █", "▀   █", " █   "});
        charMap.put('8', new String[] {"██", "█▄▄█", "█▀▀█", "██"});
        charMap.put('9', new String[] {"████", "█▄▄█", "▀▀▀█", "███  "});
    }

}
