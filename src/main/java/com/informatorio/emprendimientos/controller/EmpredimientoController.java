package com.informatorio.emprendimientos.controller;

import com.informatorio.emprendimientos.dto.OperationEmprendimiento;
import com.informatorio.emprendimientos.entity.Emprendimiento;
import com.informatorio.emprendimientos.repository.EmprendimientoRepository;
import com.informatorio.emprendimientos.service.EmprendimientoService;
import com.informatorio.emprendimientos.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/Emprendimientos")
public class EmpredimientoController {

    private final EmprendimientoService empServ;
    private final EmprendimientoRepository empRepo;
    private final EventService eventService;

    @Autowired
    public EmpredimientoController(EmprendimientoService empServ, EmprendimientoRepository empRepo, EventService eventService) {
        this.empServ = empServ;
        this.empRepo = empRepo;
        this.eventService = eventService;
    }

    @PostMapping(value = "/Create")
    public ResponseEntity<?> create(@Valid @RequestBody OperationEmprendimiento empOp) {
        return new ResponseEntity<>(empServ.create(empOp), HttpStatus.CREATED);
    }

    @PutMapping(value = "/Update/")
    public ResponseEntity<?> update(@RequestParam Long Id, @Valid @RequestBody OperationEmprendimiento opemp) {
        Optional<Emprendimiento> emp = empRepo.findById(Id);
        if(emp.isPresent()){
            empRepo.save(empServ.update(emp.get(), opemp));
            return new ResponseEntity<>("Emprendimiento modificado correctamente!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Emprendimiento con id: " + Id + " no existe!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/Delete")
    public ResponseEntity<?> deleteById(@RequestBody Long Id) {
        Optional<Emprendimiento> emp = empRepo.findById(Id);
        if(emp.isPresent()){
            empRepo.deleteById(Id);
            return new ResponseEntity<>("Emprendimiento borrado correctamente!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Emprendimiento con id: " + Id + " no existe!", HttpStatus.OK);
    }

    @GetMapping(value = "/GetAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity(empRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/GetAllByTag")
    public ResponseEntity<?> getAllByTag(@RequestBody String tag) {
        var tagf = StringUtils.capitalize(tag);
        var emps = empRepo.findAll();
        var empsf = emps.stream()
                .filter(e -> e.getTags().stream()
                .anyMatch(t -> t.getName().contains(tagf))).collect(Collectors.toList());
        return new ResponseEntity(empsf, HttpStatus.OK);
    }

    @GetMapping(value = "/GetAllNotPublished")
    public ResponseEntity<?> getAllNoPublished() {
        var emps = empRepo.findAll().stream()
                .filter(e -> !e.isPublished()).collect(Collectors.toList());
        return new ResponseEntity(emps, HttpStatus.OK);
    }

    @PostMapping(value = "/RegisterToEvent")
    public ResponseEntity<?> registerToEvent(@RequestParam Long empId, @RequestParam Long eventId) {
        return new ResponseEntity<>(eventService.register(empId, eventId), HttpStatus.CREATED);
    }
}