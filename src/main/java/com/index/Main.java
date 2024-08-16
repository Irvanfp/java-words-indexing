package com.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    Properties properties = new Properties();

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            String file = properties.getProperty("file.path");

            Map<String, Object> indexResult = indexing(file);
            
            int number = (int) indexResult.get("number");
            List<String> words = (List<String>) indexResult.get("words");

            System.out.println("number of words start with M: " + number);
            System.out.println("all the words longer than 5 chars: ");
            for (String word : words) {
                System.out.println(word);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static Map<String, Object> indexing(String file) {
        Map<String, Object> result = new HashMap<>();

        List<String> selectedWords = new ArrayList<>();
        int numberOfMs = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            Pattern validWords = Pattern.compile("^[a-zA-Z]+$");

            for (String s = br.readLine(); s != null; s = br.readLine()) {
                String[] words = s.split("\\s+");
                for (String word : words) {
                    if (validWords.matcher(word).matches()) {
                        if (word.toLowerCase().startsWith("m")) {
                            numberOfMs++;
                        }

                        if (word.length() > 5) {
                            selectedWords.add(word);
                        }
                    }
                }
            }
            result.put("number", numberOfMs);
            result.put("words", selectedWords);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
}