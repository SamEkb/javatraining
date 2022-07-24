package ru.skilanov.reflection;

import ru.skilanov.annotations.After;
import ru.skilanov.annotations.Before;
import ru.skilanov.annotations.Test;

public class AnnotationsTest {

    @Before
    public String startupEnvironment() {
        return "Environment ready";
    }

    @After
    public String closeAllResources() {
        return "All resources were closed";
    }

    @Test
    public String getFirstMethodInfo() {
        return "First method info";
    }

    @Test
    public String getSecondMethodInfo() {
        throw new RuntimeException();
    }

    @Test
    public String getThirdMethodInfo() {
        return "Third method info";
    }
}
