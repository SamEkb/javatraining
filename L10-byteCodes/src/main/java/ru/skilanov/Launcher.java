package ru.skilanov;

import ru.skilanov.proxy.Ioc;

public class Launcher {
    public static void main(String[] args) {
        var testLogging = Ioc.createLoggingClass();
        testLogging.calculation(88);
        testLogging.calculation(1,2);
        testLogging.divide(1);
    }
}
