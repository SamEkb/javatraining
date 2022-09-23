package ru.skilanov.processor.homework;

import ru.skilanov.model.Message;
import ru.skilanov.processor.Processor;

public class EvenSecondExceptionProcessor implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public EvenSecondExceptionProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
            throw new EvenSecondException("The second is even");
        }
        return message;
    }
}
