package com.SoftwareOrdersUberEats.orderService.service;

import com.SoftwareOrdersUberEats.orderService.entity.ProcessedEventEntity;
import com.SoftwareOrdersUberEats.orderService.repository.ProcessedEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessedEventService {

    private ProcessedEventRepository processedEventRepository;

    public void save(UUID id){
        try{
            processedEventRepository.save(ProcessedEventEntity.builder()
                    .id(id)
                    .processedAt(Instant.now())
                    .build());
        }catch(Exception e){
            //log event already exist
        }
    }

    public boolean isEventProcessed(UUID id){
        return processedEventRepository.existsById(id);
    }
}
