package ru.skilanov.processor.homework;

import ru.skilanov.model.Message;
import ru.skilanov.processor.Processor;

public class ChangeFieldsProcessor implements Processor {
    private final Processor processor;

    public ChangeFieldsProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        return message.toBuilder()
                .field11(message.getField12())
                .field12(message.getField11())
                .build();
    }
}
