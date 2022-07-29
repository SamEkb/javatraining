package ru.skilanov.reflection;

import ru.skilanov.annotations.After;
import ru.skilanov.annotations.Before;
import ru.skilanov.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.skilanov.reflection.TestStatus.FAILED;
import static ru.skilanov.reflection.TestStatus.PASSED;

/**
 * Class that launched methods which marked with annotation by reflection.
 */
public class MethodsRunner {
    public void runMethods(Class clazz) throws ClassNotFoundException {
        var allTestsResult = new ArrayList<String>();
        for (Method method : getClassMethodsByAnnotation(clazz, Test.class)) {
            var failed = FAILED.name();
            var passed = PASSED.name();
            var currentTestResults = new HashSet<String>();
            var newInstance = createNewInstance(clazz);

            currentTestResults.add(launchPreparatoryOrFinalMethods(clazz, newInstance, Before.class));

            if (!currentTestResults.contains(FAILED.name())) {
                currentTestResults.add(launchTestMethod(method, newInstance));
            }

            currentTestResults.add(launchPreparatoryOrFinalMethods(clazz, newInstance, After.class));
            var result = currentTestResults.contains(failed) ? failed : passed;
            allTestsResult.add(result);
        }
        showTestsResults(allTestsResult);
    }

    private Object createNewInstance(Class clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Method> getClassMethodsByAnnotation(Class clazz, Class annotation) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter(it -> it.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private String launchPreparatoryOrFinalMethods(Class clazz, Object instance, Class annotation, Object... args) {
        for (Method method : getClassMethodsByAnnotation(clazz, annotation)) {
            method.setAccessible(true);
            try {
                method.invoke(instance, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                return TestStatus.FAILED.name();
            }
        }
        return TestStatus.PASSED.name();
    }

    private String launchTestMethod(Method method, Object instance, Object... args) {
        method.setAccessible(true);
        try {
            method.invoke(instance, args);
            return TestStatus.PASSED.name();
        } catch (IllegalAccessException | InvocationTargetException e) {
            return TestStatus.FAILED.name();
        }
    }

    private void showTestsResults(List<String> results) {
        var totalTests = results.size();
        var passedTests = results.stream().filter(it -> it.equals(PASSED.name())).toList().size();
        var failedTest = results.stream().filter(it -> it.equals(FAILED.name())).toList().size();

        System.out.println("Total: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTest);
    }
}
