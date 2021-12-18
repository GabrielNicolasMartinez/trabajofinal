package com.informatorio.emprendimientos.controller;

import com.informatorio.emprendimientos.dto.OperationEvent;
import com.informatorio.emprendimientos.entity.Event;
import com.informatorio.emprendimientos.entity.User;
import com.informatorio.emprendimientos.repository.EventRepository;
import com.informatorio.emprendimientos.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("Events/")
public class EventController {
    private final EventService eventServ;
    private final EventRepository eventRepo;

    public EventController(EventService eventServ, EventRepository eventRepo) {
        this.eventServ = eventServ;
        this.eventRepo = eventRepo;
    }

    @PostMapping(value = "/Create")
    public ResponseEntity<?> create(@Valid @RequestBody Event eventOp) {
        return new ResponseEntity<>(eventRepo.save(eventOp), HttpStatus.CREATED);
    }

    @PutMapping(value = "/Update/")
    public ResponseEntity<?> update(@RequestParam Long Id, @Valid @RequestBody Event event) {
        Optional<Event> dbEvent = eventRepo.findById(Id);
        if(dbEvent.isPresent()){
            eventRepo.save(eventServ.update(dbEvent.get(), event));
            return new ResponseEntity<>("Evento modificado correctamente!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Evento con id: " + Id + " no existe!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/Delete")
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        Optional<Event> eventf = eventRepo.findById(id);
        if(eventf.isPresent()){
            eventRepo.deleteById(id);
            return new ResponseEntity<>("Evento borrado correctamente!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Evento con id: " + id + " no existe!", HttpStatus.OK);
    }

    @GetMapping("/Ranking/")
    public ResponseEntity<?> getEventRanking(@RequestParam Long id) {
        return new ResponseEntity<>(eventRepo.findById(id), HttpStatus.OK);
    }
}