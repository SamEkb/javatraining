package ru.skilanov.proxy;

import ru.skilanov.annotation.Log;
import ru.skilanov.service.TestLoggingImpl;
import ru.skilanov.service.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
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

            var annotatedMethods = getAnnotatedMethods();

            printAnnotatedMethodInformation(annotatedMethods, method, args);

            return method.invoke(testLoggingInterface, args);
        }

        private void printAnnotatedMethodInformation(Set<Method> annotatedMethods,
                                                     Method currentMethod,
                                                     Object[] currentMethodArguments) {
            annotatedMethods.forEach(annotatedMethod -> {
                if (isMethodEqual(currentMethod, annotatedMethod)) {
                    System.out.println("executed method: " + annotatedMethod.getName() +
                            " params: " + Arrays.toString(currentMethodArguments));
                }
            });
        }

        private boolean isMethodEqual(Method method, Method annotatedMethod) {
            return method.getName().equals(annotatedMethod.getName()) &&
                    Arrays.equals(method.getParameterTypes(), annotatedMethod.getParameterTypes());
        }

        private Set<Method> getAnnotatedMethods() {
            return Stream.of(testLoggingInterface.getClass().getDeclaredMethods())
                    .filter(it -> it.isAnnotationPresent(Log.class))
                    .collect(Collectors.toSet());
        }
    }
}
