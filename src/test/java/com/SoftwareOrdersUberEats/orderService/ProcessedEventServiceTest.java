package com.SoftwareOrdersUberEats.orderService;

import com.SoftwareOrdersUberEats.orderService.entity.ProcessedEventEntity;
import com.SoftwareOrdersUberEats.orderService.repository.ProcessedEventRepository;
import com.SoftwareOrdersUberEats.orderService.service.ProcessedEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessedEventServiceTest {

    @Mock
    private ProcessedEventRepository processedEventRepository;

    @InjectMocks
    private ProcessedEventService processedEventService;

    @Test
    @DisplayName("Should save processed event successfully when it is new")
    void save_Success() {
        UUID eventId = UUID.randomUUID();

        processedEventService.save(eventId);

        verify(processedEventRepository).save(any(ProcessedEventEntity.class));
    }

    @Test
    @DisplayName("Should handle exception and log warning when event is already processed")
    void save_ShouldCatchException_WhenDuplicateId() {
        UUID eventId = UUID.randomUUID();
        doThrow(new RuntimeException("Unique constraint violation")).when(processedEventRepository).save(any());

        assertDoesNotThrow(() -> processedEventService.save(eventId));
        verify(processedEventRepository).save(any());
    }

    @Test
    @DisplayName("Should return true when event exists in repository")
    void isEventProcessed_True() {
        UUID eventId = UUID.randomUUID();
        when(processedEventRepository.existsById(eventId)).thenReturn(true);

        boolean result = processedEventService.isEventProcessed(eventId);

        assertTrue(result);
        verify(processedEventRepository).existsById(eventId);
    }

    @Test
    @DisplayName("Should return false when event does not exist in repository")
    void isEventProcessed_False() {
        UUID eventId = UUID.randomUUID();
        when(processedEventRepository.existsById(eventId)).thenReturn(false);

        boolean result = processedEventService.isEventProcessed(eventId);

        assertFalse(result);
        verify(processedEventRepository).existsById(eventId);
    }
}