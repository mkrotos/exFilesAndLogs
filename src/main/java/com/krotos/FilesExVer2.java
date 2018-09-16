package com.krotos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilesExVer2 {
    public static final String START_DIR_FOR_FIND_TXT_FILES = "C:/totalcmd";
    public static final String DEFAULT_EMPTY_FIRST_LINE = "***empty file***";
    public static final String RESULT_FILE_PATH = "ex2.txt";

    public static void main(String[] args) throws IOException {

        List<Path> listOfTxtFilePaths = findTxtFiles();
        Map<String, String> pathAndFirstLines = getFirstLinesFromFiles(listOfTxtFilePaths);
        writeToFile(pathAndFirstLines);
    }

    private static List<Path> findTxtFiles() throws IOException {
        Path path = Paths.get(START_DIR_FOR_FIND_TXT_FILES);
        int maxDepth = 50;

        return Files.walk(path, maxDepth)
                .filter(byExtension("txt"))
                .collect(Collectors.toList());
    }

    private static Predicate<Path> byExtension(String extension) {
        return file -> file.toString().endsWith("." + extension)||file.toString().endsWith("."+extension.toUpperCase());
    }

    private static Map<String, String> getFirstLinesFromFiles(List<Path> listOfTxtFilePath) {
        Map<String, String> pathAndFirstLine = new HashMap<>();

        listOfTxtFilePath.forEach(path -> {
            String pathName = path.toString();
            String firstLine = getFirstLine(path);

            pathAndFirstLine.put(pathName, firstLine);
        });

        return pathAndFirstLine;
    }

    private static String getFirstLine(Path path){
        try {
            return Files.lines(path)
                    .findFirst().orElse(DEFAULT_EMPTY_FIRST_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void writeToFile(Map<String, String> pathAndFirstLine) throws IOException {
        Path path = Paths.get(RESULT_FILE_PATH);
        List<String> linesToWrite = new ArrayList<>();

        pathAndFirstLine
                .forEach((filePath, firstLine) -> {
                    String lineToWrite = String.format("%s - %s", filePath, firstLine);
                    linesToWrite.add(lineToWrite);
                });

        Files.write(path, linesToWrite);
    }
}

