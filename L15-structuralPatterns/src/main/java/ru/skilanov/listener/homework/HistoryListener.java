package ru.skilanov.listener.homework;

import ru.skilanov.listener.Listener;
import ru.skilanov.model.Message;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Deque<Message> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message msg) {
        var loggedMsg = msg.toBuilder()
                .field13(msg.getField13().copy())
                .build();
        stack.add(loggedMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        for (Message message : stack) {
            var currentMessageId = message.getId();
            if (currentMessageId == id) {
                return Optional.of(message);
            }
        }
        return Optional.empty();
    }
}
