package ru.skilanov.service;

import ru.skilanov.annotation.Log;

public interface TestLoggingInterface {
    void calculation(int param);

    void calculation(int x, int y);

    void divide(int param);
}
