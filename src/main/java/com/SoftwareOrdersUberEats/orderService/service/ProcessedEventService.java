package com.SoftwareOrdersUberEats.orderService.service;

import com.SoftwareOrdersUberEats.orderService.entity.ProcessedEventEntity;
import com.SoftwareOrdersUberEats.orderService.repository.ProcessedEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.SoftwareOrdersUberEats.orderService.constant.TracerConstants.EXCEPTION_ALREADY_EVENT_PROCESSED;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessedEventService {

    private ProcessedEventRepository processedEventRepository;

    public void save(UUID id){
        try{
            processedEventRepository.save(ProcessedEventEntity.builder()
                    .id(id)
                    .processedAt(Instant.now())
                    .build());
        }catch(Exception e){
            log.warn(EXCEPTION_ALREADY_EVENT_PROCESSED, id);
        }
    }

    public boolean isEventProcessed(UUID id){
        return processedEventRepository.existsById(id);
    }
}
