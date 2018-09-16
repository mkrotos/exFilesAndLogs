package com.krotos.files;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class FilesBasics {

    public static void main(String[] args) throws IOException {

        //printListOfFiles();
        //printDetails();
        readFile();
        //writeFile();
        findFiles();
    }

    private static void findFiles() throws IOException {
        String fileName="D:\\kody\\Java\\Exercises\\exFilesLogs\\";
        Path dir=Paths.get(fileName);
        int maxDepth=5;

        Files.walk(dir,maxDepth).forEach(System.out::println);

    }

    private static void printDetails() throws IOException {
        String fileName="C:\\swapfile.sys";
        Path path=Paths.get(fileName);

        BasicFileAttributes attr= java.nio.file.Files.readAttributes(path,BasicFileAttributes.class);

        System.out.println("create time: "+attr.creationTime());
        System.out.println("last access time: "+attr.lastAccessTime());
        System.out.println("size: "+attr.size()/1024/1024);
        System.out.println("last modify time: "+attr.lastModifiedTime());
        System.out.println("is directory: "+attr.isDirectory());
    }

    private static void printListOfFiles() throws IOException {
        String startPath="C:/";
        Path dir = Paths.get(startPath);
        java.nio.file.Files.list(dir)
                .forEach(System.out::println);
    }

    private static void readFile() throws IOException {
        String fileName="D:\\kody\\Java\\Exercises\\exFilesLogs\\tekst.txt";
        Path dir=Paths.get(fileName);
        java.nio.file.Files.lines(dir)
                .forEach(System.out::println);
    }

    private static void writeFile() throws IOException {
        String fileName="C:\\Users\\makro\\Desktop\\tmp.txt";
        Path path=Paths.get(fileName);
        List<String> linesToWrite= Arrays.asList("Linia 1","Linia 2", "Linia 3");

        Files.write(path,linesToWrite);
    }
}
