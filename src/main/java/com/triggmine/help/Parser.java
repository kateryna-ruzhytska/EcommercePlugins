package com.triggmine.help;

import java.util.Scanner;

/**
 * Created by sromanov on 02.04.15.
 */
public class Parser {
    private static String credentials;
    public static String getCredentials(String text, String type) {
        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(type)) {
                credentials = line.replaceAll(type, "");
                break;
            }
        }
        scanner.close();
        return credentials;
    }
}
