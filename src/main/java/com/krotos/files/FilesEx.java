package com.krotos.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FilesEx {

    public static void main(String[] args) throws IOException {
        findTxtAndWriteFirstLines();
    }


    private static void findTxtAndWriteFirstLines() throws IOException {
        String pathString = "C:/totalcmd";
        Path pathForFiles = Paths.get(pathString);
        int depth = 5;
        Files.walk(pathForFiles, depth).forEach(System.out::println);

        String pathToNewFile = "ex1.txt";
        Path newPath = Paths.get(pathToNewFile);
        if (!Files.exists(newPath)) {
            Files.createFile(newPath);
        }

        Map<String, String> mapOfLines = new HashMap<>();

        List<Path> pathList = Files.walk(pathForFiles, depth)
                .filter(FilesEx::filterByTxtExtension)
                .collect(Collectors.toList());

        //System.out.println(paths);

        for (Path path : pathList) {
            String name = path.getFileName().toString();
            String line = Files.lines(path).findFirst().orElse("");
            mapOfLines.put(name, line);
        }
        //System.out.println(mapOfLines);
        List<String> listOfEntryToFile = new ArrayList<>();

        for (Map.Entry entry : mapOfLines.entrySet()) {
            String line = formatEntryToStringLine(entry);
            listOfEntryToFile.add(line);
        }

        Files.write(newPath, listOfEntryToFile);
    }

    private static String formatEntryToStringLine(Map.Entry entry) {
        return entry.getKey().toString()+" : "+entry.getValue().toString();
    }

    private static boolean filterByTxtExtension(Path file) {
        return file.toString().endsWith(".TXT") || file.toString().endsWith(".txt");
    }
}
