package com.krotos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilesEx {

    public static void main(String[] args) throws IOException {
        findTxtAndWriteFirstLines();
    }


    private static void findTxtAndWriteFirstLines() throws IOException {
        String pathString = "C:/totalcmd";
        Path path = Paths.get(pathString);
        int depth = 5;
        //Files.walk(path, depth).forEach(System.out::println);
        String pathToNewFile = "ex1.txt";
        Path newPath = Paths.get(pathToNewFile);
        if (!Files.exists(newPath)) {
            Files.createFile(newPath);
        }

        Map<String, String> mapOfLines = new HashMap<>();

        List<Path> paths = Files.walk(path, depth)
                .filter(file -> file.toString().endsWith(".TXT") || file.toString().endsWith(".txt"))
                .collect(Collectors.toList());

        //System.out.println(paths);

        for (Path path1 : paths) {
            String name = path1.getFileName().toString();
            String line = Files.lines(path1).findFirst().get();
            mapOfLines.put(name, line);
        }
        //System.out.println(mapOfLines);
        List<String> listOfEntryToFile = new ArrayList<>();

        for (Map.Entry entry : mapOfLines.entrySet()) {
            String line = entry.getKey().toString()+" : "+entry.getValue().toString();
            listOfEntryToFile.add(line);

        }
        Files.write(newPath, listOfEntryToFile);


    }
}
