package ru.skilanov.proxy;

import ru.skilanov.service.TestLoggingImpl;
import ru.skilanov.service.TestLoggingInterface;
import ru.skilanov.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ioc {

    public static TestLoggingInterface createLoggingClass() {
        var handler = new CustomInvocationHandler(new TestLoggingImpl());


        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingInterface;

        public CustomInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.testLoggingInterface = testLoggingInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            var annotatedMethods = Stream.of(testLoggingInterface.getClass().getDeclaredMethods())
                    .filter(it -> it.isAnnotationPresent(Log.class))
                    .collect(Collectors.toSet());

            annotatedMethods.forEach(it -> {
                if (method.getName().equals(it.getName()) &&
                        method.getParameterTypes().length == it.getParameterTypes().length) {
                    System.out.println("executed method: " + it.getName() + " param: " + Arrays.toString(args));
                }
            });

            return method.invoke(testLoggingInterface, args);
        }
    }
}
