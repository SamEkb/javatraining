package ru.skilanov.listener.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.skilanov.model.Message;
import ru.skilanov.processor.homework.DateTimeProvider;
import ru.skilanov.processor.homework.EvenSecondException;
import ru.skilanov.processor.homework.EvenSecondExceptionProcessor;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class EvenSecondExceptionProcessorTest {
   private DateTimeProvider dateTimeProvider;
   private EvenSecondExceptionProcessor evenSecondExceptionProcessor;

   @BeforeEach
    private void init() {
       dateTimeProvider = Mockito.mock(DateTimeProvider.class);
       evenSecondExceptionProcessor = new EvenSecondExceptionProcessor(dateTimeProvider);
   }

    @Test
    void processOddSeconds() {
        when(dateTimeProvider.getDate()).thenReturn(LocalDateTime.of(
                2022, 12, 31, 23, 59, 1
        ));

        var message = new Message.Builder(1111L).field5("field5").build();
        var result = evenSecondExceptionProcessor.process(message);

        assertThat(result).isEqualTo(message);

    }

    @Test
    void processEvenSeconds() {
        when(dateTimeProvider.getDate()).thenReturn(LocalDateTime.of(
                2022, 12, 31, 23, 59, 2
        ));
        var message = new Message.Builder(2222L).field5("field5").build();

        assertThrows(EvenSecondException.class, () -> evenSecondExceptionProcessor.process(message));

    }
}
