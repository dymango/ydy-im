package com.dyman.im;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author dyman
 * @describe 枚举类缓存
 * @date 2020/7/8
 */
public class EnumCache {

    private static Map<String, Map<Integer, Object>> cache = new HashMap<>();
    private final static String CLASS_PATH = "classpath:";
    private final static String ENUM_GETCODE = "getCode";
    private final static String ENUM_GETNAME = "getName";
    private final static String CLASSES_PACKAGE_NAME = "classes";

    public static void init() throws FileNotFoundException {
        recurse(new File(ResourceUtils.getURL(CLASS_PATH).getPath()).listFiles());
    }

    public static Enum get(String key, Integer code) {
        Object val = cache.get(key).get(code);
        return (Enum) val;
    }

    private static void recurse(File[] files) {
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                recurse(f.listFiles());
            } else {
                try {
                    String fileName = f.getName();
                    if (fileName.contains(".class")) {
                        String p = f.getAbsolutePath();
                        String[] split = p.split("\\\\");
                        boolean start = false;
                        String packageName = "";
                        for (int j = 0; j < split.length - 1; j++) {
                            if(start) {
                                packageName += split[j] + ".";
                            }

                            if (split[j].equals(CLASSES_PACKAGE_NAME)) {
                                start = true;
                            }
                        }

                        Class<?> aClass = Class.forName(packageName + getClassName(fileName));
                        boolean anEnum = aClass.isEnum();
                        if(anEnum) {
                            if(cache.get(aClass.getSimpleName()) == null) cache.put(aClass.getSimpleName(), new HashMap<>());
                            Object[] enumConstants = aClass.getEnumConstants();
                            for (Object cons : enumConstants) {
                                cache.get(aClass.getSimpleName()).put((int)cons.getClass().getMethod(ENUM_GETCODE).invoke(cons), cons);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static String getClassName(String fileName) {
        for (int i = 0; i < fileName.length(); i++) {
            if(fileName.charAt(i) == '.') return fileName.substring(0, i);
        }

        return fileName;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\dyman\\.m2\\repository\\com\\alibaba\\fastjson\\1.2.71\\fastjson-1.2.71.jar";
//        List<String> myClassName = new ArrayList<String>();
//        String[] jarInfo = filePath.split("\\\\");
//        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
//        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(filePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (true) {
//                        if (entryName.startsWith(packagePath)) {
//                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
//                            myClassName.add(entryName);
//                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
//                        if (myPackagePath.equals(packagePath)) {
//                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
//                            myClassName.add(entryName);
//                        }
                    }
                }
            }
        } catch (Exception e) {
            //SystemLog.Log(LogType.systemInfo, e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }
}
