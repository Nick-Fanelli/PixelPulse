package com.nickfanelli.engine.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EngineUtils {

    public static String loadEngineResourceFileAsText(String filepath) {

        URL url = Thread.currentThread().getContextClassLoader().getResource(filepath);

        if(url == null)
            throw new RuntimeException("Can not load engine resource at: " + filepath + " if URL is null");

        try(Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8)) {

            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
