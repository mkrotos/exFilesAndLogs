package com.krotos;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class ex1 {

    public static void main(String[] args) throws IOException {

        printListOfFiles();
        printDetails();
        readFile();
    }

    private static void printDetails() throws IOException {
        String fileName="C:\\swapfile.sys";
        Path path=Paths.get(fileName);

        BasicFileAttributes attr= Files.readAttributes(path,BasicFileAttributes.class);

        System.out.println("create time: "+attr.creationTime());
        System.out.println("last access time: "+attr.lastAccessTime());
        System.out.println("size: "+attr.size()/1024/1024);
        System.out.println("last modify time: "+attr.lastModifiedTime());
        System.out.println("is directory: "+attr.isDirectory());
    }

    private static void printListOfFiles() throws IOException {
        String startPath="C:/";
        Path dir = Paths.get(startPath);
        Files.list(dir)
                .forEach(System.out::println);
    }

    private static void readFile() throws IOException {
        String fileName="D:\\kody\\Java\\Exercises\\exFilesLogs\\tekst.txt";
        Path dir=Paths.get(fileName);
        Files.lines(dir)
                .forEach(System.out::println);
    }
}
