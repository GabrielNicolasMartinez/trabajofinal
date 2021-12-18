package com.informatorio.emprendimientos.service;

import com.informatorio.emprendimientos.dto.OperationEvent;
import com.informatorio.emprendimientos.entity.Emprendimiento;
import com.informatorio.emprendimientos.entity.Event;
import com.informatorio.emprendimientos.entity.StatusType;
import com.informatorio.emprendimientos.repository.EmprendimientoRepository;
import com.informatorio.emprendimientos.repository.EventRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@Configuration
@EnableScheduling
public class EventService {

    private final EventRepository eventRepo;
    private final EmprendimientoRepository empRepo;;

    public EventService(EventRepository eventRepository, EmprendimientoRepository emprendimientoRepository) {
        this.eventRepo = eventRepository;
        this.empRepo = emprendimientoRepository;
    }

    public Event update(Event inDB, Event event) {
        if (event.getName() != null) {
            inDB.setName(event.getName());
        }
        if (event.getDetails() != null) {
            inDB.setDetails(event.getDetails());
        }
        if (event.getWinnerReward() != null) {
            inDB.setWinnerReward(event.getWinnerReward());
        }
        if (event.getEndDate() != null) {
            inDB.setEndDate(event.getEndDate());
        }
        if (event.getStatus() != null) {
            inDB.setStatus(event.getStatus());
        }
        return inDB;
    }

    public Emprendimiento register(Long empId, Long eventId) {
        Emprendimiento emp = empRepo.getById(empId);
        Event event1 = eventRepo.getById(eventId);
        if(event1.getStatus() == StatusType.OPEN) {
            emp.getEvents().add(event1);
            System.out.println("Se ha subscrito a este evento correctamente.");
        } else if (event1.getStatus() == StatusType.IN_COURSE){
            System.out.println("El tiempo de subscripcion a este evento a finalizado.");
        } else{
            System.out.println("Este evento a finalizado.");
        }
        return empRepo.save(emp);
    }
}
