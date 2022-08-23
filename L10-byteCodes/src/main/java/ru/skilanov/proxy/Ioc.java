package ru.skilanov.proxy;

import ru.skilanov.annotation.Log;
import ru.skilanov.service.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Ioc {
    protected Set<Method> annotatedMethods = new HashSet<>();

    public TestLoggingInterface createLoggingClass(TestLoggingInterface testLoggingInterface) {
        var handler = new CustomInvocationHandler(testLoggingInterface);

        addAnnotatedMethods(testLoggingInterface);

        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    private void addAnnotatedMethods(TestLoggingInterface testLoggingInterface) {
        Stream.of(testLoggingInterface.getClass().getDeclaredMethods())
                .filter(it -> it.isAnnotationPresent(Log.class))
                .forEach(annotatedMethods::add);
    }

    class CustomInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingInterface;

        public CustomInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.testLoggingInterface = testLoggingInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            printAnnotatedMethodInformation(method, args);

            return method.invoke(testLoggingInterface, args);
        }

        private void printAnnotatedMethodInformation(Method currentMethod,
                                                     Object[] currentMethodArguments) {
            for (Method annotatedMethod : annotatedMethods) {
                if (isMethodEqual(currentMethod, annotatedMethod)) {
                    System.out.println("executed method: " + annotatedMethod.getName() +
                            " params: " + Arrays.toString(currentMethodArguments));
                    break;
                }
            }
        }

        private boolean isMethodEqual(Method method, Method annotatedMethod) {
            return method.getName().equals(annotatedMethod.getName()) &&
                    Arrays.equals(method.getParameterTypes(), annotatedMethod.getParameterTypes());
        }
    }
}
