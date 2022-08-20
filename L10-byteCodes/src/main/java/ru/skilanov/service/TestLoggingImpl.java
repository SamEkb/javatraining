package ru.skilanov.service;

import ru.skilanov.annotation.Log;

public class TestLoggingImpl implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {

    }

    @Log
    @Override
    public void calculation(int x, int y) {

    }

    @Log
    @Override
    public void divide(int param) {

    }
}
