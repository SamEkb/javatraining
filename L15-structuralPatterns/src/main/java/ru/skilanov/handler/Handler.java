package ru.skilanov.handler;

import ru.skilanov.model.Message;
import ru.skilanov.listener.Listener;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
