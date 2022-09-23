package ru.skilanov.listener.homework;

import ru.skilanov.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
