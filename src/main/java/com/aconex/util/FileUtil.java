package com.aconex.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class FileUtil {

    public static List<String> readFileByJdk8(String file) throws Exception {
        Path path = Paths.get(FileUtil.class.getClassLoader().getResource(file).toURI());

        List<String> resultList = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(resultList::add);
        }

        return resultList;
    }

    public static List<String> readAbsolutePathFileByJdk8(String file) throws Exception {
        Path path = Paths.get(file);

        List<String> resultList = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(resultList::add);
        }

        return resultList;
    }
}
