package com.saith.dota.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangjiawen
 * @version 1.0
 * @date 2021/1/25 2:13 下午
 */
public class CommonUtils {

    public static List<String> readList(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.map(String::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeToList(String fileName, List<String> listToWrite) {
        Path out = Paths.get(fileName);
        try {
            Files.write(out, listToWrite, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String filePath) {
        Path p = Paths.get(filePath);
        return p.getFileName().toString();
    }
}
