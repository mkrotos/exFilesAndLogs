package com.krotos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FirstLogEx {

    public static void main(String[] args) throws IOException {
        dispCountsOfFileFromats();
    }

    private static void dispCountsOfFileFromats() throws IOException {
        String pathString = "C:/totalcmd";
        Path pathForFiles = Paths.get(pathString);
        int depth = 10;
        //Files.walk(pathForFiles, depth).forEach(System.out::println);


        Set<String> uniqueExtensions = Files.walk(pathForFiles, depth).filter(onlyFiles()).map(FirstLogEx::getExtension).collect(Collectors.toSet());
        //System.out.println(uniqueExtensions);

        for (String extension : uniqueExtensions) {
            long numberOfInstances = Files.walk(pathForFiles).filter(path -> path.getFileName().toString().endsWith("." + extension)).count();
            System.out.println(extension + " - " + numberOfInstances);
        }
    }

    private static Predicate<Path> onlyFiles() {
        return path -> {
            try {
                return Files.readAttributes(path, BasicFileAttributes.class).isRegularFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    private static String getExtension(Path path) {
        try {
            String[] splitedPath = path.toString().split("\\.");
            return splitedPath[splitedPath.length - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "without ext";
        }
    }
}
