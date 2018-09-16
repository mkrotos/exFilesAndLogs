package com.krotos.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class FirstLogEx {

    public static final String WITHOUT_EXTENSION_DEFAULT = "without ext";

    public static void main(String[] args) throws IOException {
        dispAverageWeightsOfFileFormats();
        System.out.println("---------------------------");
        dispCountsOfFileFormats();

    }

    private static void dispAverageWeightsOfFileFormats() throws IOException {
        String pathString = "C:/totalcmd";
        Path pathForFiles = Paths.get(pathString);
        int depth = 10;
        //Files.walk(pathForFiles, depth).forEach(System.out::println);

        Set<String> uniqueExtensions = Files.walk(pathForFiles, depth)
                .filter(Files::isRegularFile)
                .map(FirstLogEx::getExtension)
                .collect(Collectors.toSet());
        //System.out.println(uniqueExtensions);

        for (String extension : uniqueExtensions) {
            long numberOfInstances =(long) Files.walk(pathForFiles)
                    .filter(pathsWithSpecifiedExtension(extension))
                    .mapToLong(mapPathToFileSize())
                    .average().orElse(0);

            dispLineOfStringAndLong(extension, numberOfInstances);
        }
    }

    private static ToLongFunction<Path> mapPathToFileSize() {
        return path-> {
            try {
                return Files.readAttributes(path,BasicFileAttributes.class).size();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        };
    }

    private static void dispCountsOfFileFormats() throws IOException {
        String pathString = "C:/totalcmd";
        Path pathForFiles = Paths.get(pathString);
        int depth = 10;
        //Files.walk(pathForFiles, depth).forEach(System.out::println);

        List<String> nonUniqExtensions=Files
                .walk(pathForFiles, depth)
                .filter(Files::isRegularFile)
                .map(FirstLogEx::getExtension)
                .collect(Collectors.toList());

        Map<String,Long> results= nonUniqExtensions.stream()
                .collect(Collectors.groupingBy(s->s,Collectors.counting()));

        for (Map.Entry entry:results.entrySet()){
            dispLineOfStringAndLong(entry.getKey().toString(), (long) entry.getValue());
        }
    }

    private static void dispLineOfStringAndLong(String extension, long numberOfInstances) {
        String lineToDisplay = String.format("%s - %s", extension, numberOfInstances);
        System.out.println(lineToDisplay);
    }

    private static Predicate<Path> pathsWithSpecifiedExtension(String extension) {
        return path -> path.getFileName().toString().endsWith("." + extension);
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
