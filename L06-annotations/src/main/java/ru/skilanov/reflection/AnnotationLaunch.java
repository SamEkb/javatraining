package ru.skilanov.reflection;

public class AnnotationLaunch {
    public static void main(String[] args) {
        try {
            new MethodsRunner().runMethods(AnnotationsTest.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
