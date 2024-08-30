package com.github.manjunathprabhakar.core.internal;

import com.github.manjunathprabhakar.core.annotations.SpectateOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationScanner {

    private static String getFullyQualifiedClassName(String packageStmt, String fileName) {
        String imp = packageStmt.replaceAll("package", "").replaceAll(";", "").trim();
        int i = fileName.lastIndexOf('.');
        String substring = fileName.substring(0, i).trim();
        return imp + "." + substring;
    }

    public Map<String, Object> scanForSpectate() throws IOException {

        String property = System.getProperty("user.dir");

        //Go to each file in "user.dir", and get only those files ending with .java extension
        List<File> collect = Files.walk(Paths.get(property))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(z -> z.getName().endsWith(".java"))
                .collect(Collectors.toList());

        //Read Each file and get the line that starts with work package, if found then get the fully qaulified class name
        Map<String, String> data = new HashMap<>();
        for (File file : collect) {
            if (file.getName().endsWith(".java")) {
                String st;
                while ((st = new BufferedReader(new FileReader(file)).readLine()) != null) {
                    if (st.trim().startsWith("package")) {
                        break;
                    }
                }
                String fullyQualifiedClassName = getFullyQualifiedClassName(st, file.getName());
                data.put(file.getName(), fullyQualifiedClassName);
            }
        }

        List<Class> classWithListener = new LinkedList<>();
        //FOr each file & fully qualified ckass name, check if it has annotation, if yes put in a list
        for (Map.Entry<String, String> stringStringEntry : data.entrySet()) {
            String value = stringStringEntry.getValue();
            try {
                Class<?> aClass = Class.forName(value.trim());
                if (aClass.isAnnotationPresent(SpectateOptions.class))
                    classWithListener.add(aClass);
            } catch (ClassNotFoundException c) {
                continue;
            }
        }

        //Do your thing.
        if (classWithListener.size() > 1)
            throw new RuntimeException(SpectateOptions.class.getName() + " annotation is in more than one class, make sure its only in one class");

        String path = ((SpectateOptions) classWithListener.get(0).getAnnotation(SpectateOptions.class)).elkPropertiesFileLocation();
        System.out.println("path = " + path);

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("propertiesFilePath", path);


        return results;
    }

}
