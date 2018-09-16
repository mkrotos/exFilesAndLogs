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

    public static final String WITHOUT_EXTENSION_DEFAULT = "without ext";

    public static void main(String[] args) throws IOException {
        dispCountsOfFileFormats();
    }

    private static void dispCountsOfFileFormats() throws IOException {
        String pathString = "C:/totalcmd";
        Path pathForFiles = Paths.get(pathString);
        int depth = 10;
        //Files.walk(pathForFiles, depth).forEach(System.out::println);

        Set<String> uniqueExtensions = Files.walk(pathForFiles, depth).filter(Files::isRegularFile).map(FirstLogEx::getExtension).collect(Collectors.toSet());
        //System.out.println(uniqueExtensions);

        for (String extension : uniqueExtensions) {
            long numberOfInstances = Files.walk(pathForFiles).filter(pathsWithSpecifiedExtension(extension)).count();
            String lineToDisplay = String.format("%s - %s", extension, numberOfInstances);
            System.out.println(lineToDisplay);
        }
    }

    private static Predicate<Path> pathsWithSpecifiedExtension(String extension) {
        return path -> path.getFileName().toString().endsWith("." + extension);
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
            String[] splittedPath = path.toString().split("\\.");
            int lastElement = splittedPath.length - 1;
            if(lastElement==0){
                return WITHOUT_EXTENSION_DEFAULT;
            }
            return splittedPath[lastElement];
        } catch (ArrayIndexOutOfBoundsException e) {
            return WITHOUT_EXTENSION_DEFAULT;
        }
    }
}
