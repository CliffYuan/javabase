package com.cliffyuan.classloader;

/**
 * 类加载流程
 *
 *
 *
 * Created by cliffyuan on 14-7-31.
 */
public class ClassLoaderFlow {
    public static void main(String[] args) throws Exception{

        ClassLoader classLoader=ClassLoaderFlow.class.getClassLoader();

        Class clazz= classLoader.loadClass("java.lang.String");

        System.out.println(clazz);


    }
}
