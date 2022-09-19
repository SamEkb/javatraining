package ru.skilanov.processor;

import ru.skilanov.model.Message;

public class LoggerProcessor implements Processor {


    private final Processor processor;

    public LoggerProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        return message.toBuilder().field10(message.getField9()).build();
    }
}
