package com.informatorio.emprendimientos.controller;

import com.informatorio.emprendimientos.dto.OperationEmprendimiento;
import com.informatorio.emprendimientos.entity.Emprendimiento;
import com.informatorio.emprendimientos.entity.User;
import com.informatorio.emprendimientos.repository.UserRepository;
import com.informatorio.emprendimientos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/Users")
public class UserController {

    private final UserRepository userRepo;
    private final UserService userServ;

    @Autowired
    public UserController(UserRepository userRepo, UserService userServ) {
        this.userRepo = userRepo;
        this.userServ = userServ;
    }

    @PostMapping(value = "/Create")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        User userf = userRepo.findByEmail(user.getEmail());
        if(userf != null){
            return new ResponseEntity<>("Error: email ya esta usado!", HttpStatus.OK);
        }
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/Update/")
    public ResponseEntity<?> update(@RequestParam Long Id, @Valid @RequestBody User user) {
        Optional<User> dbUser = userRepo.findById(Id);
        if(dbUser.isPresent()){
            userRepo.save(userServ.update(dbUser.get(), user));
            return new ResponseEntity<>("Usuario modificado correctamente!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Usuario con id: " + Id + " no existe!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/Delete")
    public ResponseEntity<?> deleteById(@Valid @RequestBody Long id) {
        Optional<User> userf = userRepo.findById(id);
        if(userf.isPresent()){
            userRepo.deleteById(id);
            return new ResponseEntity<>("Usuario borrado correctamente!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Usuario con id: " + id + " no existe!", HttpStatus.OK);
    }

    @GetMapping(value = "/GetAll")
    public ResponseEntity<?> getAll(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom) {
        if (dateFrom != null) {
            List<User> users = userRepo.findByDateOfCreationAfter(dateFrom.atStartOfDay());
            return new ResponseEntity(users, HttpStatus.OK);
        }
        return new ResponseEntity(userRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/GetAllByCity")
    public ResponseEntity<?> getAllByCity(@RequestBody String city) {
        if (city.isEmpty()) {
            return new ResponseEntity("El argumento ciudad es vacio!", HttpStatus.OK);
        }
        List<User> users = userRepo.findByCity(city);
        return new ResponseEntity(users, HttpStatus.OK);
    }
}