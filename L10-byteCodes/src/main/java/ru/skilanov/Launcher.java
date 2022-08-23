package ru.skilanov;

import ru.skilanov.proxy.Ioc;
import ru.skilanov.service.TestLoggingImpl;

public class Launcher {
    public static void main(String[] args) {
        var proxy = new Ioc();
        var testLogging = proxy.createLoggingClass(new TestLoggingImpl());
        testLogging.calculation(88);
        testLogging.calculation(1, 2);
        testLogging.calculation("test", 1);
        testLogging.divide(1);
    }
}
