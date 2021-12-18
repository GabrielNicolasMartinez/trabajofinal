package com.informatorio.emprendimientos.controller;

import com.informatorio.emprendimientos.dto.OperationVote;
import com.informatorio.emprendimientos.entity.Vote;
import com.informatorio.emprendimientos.repository.VoteRepository;
import com.informatorio.emprendimientos.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("Votes/")
public class VoteController {
    private final VoteService voteService;
    private final VoteRepository voteRepository;

    public VoteController(VoteService voteService, VoteRepository voteRepository) {
        this.voteService = voteService;
        this.voteRepository = voteRepository;
    }

    @PostMapping("Add")
    public ResponseEntity<?> add(@Valid @RequestBody OperationVote opv) {
        String res = "";
        int num = voteService.create(opv);
        switch (num){
            case 0:
                res = "Error: Emprendimiento con id: " + opv.getEmprendimientoId() + " no existe!";
                break;
            case 1:
                res = "Error: Voto ya existe"; break;
            case 2:
                res = "Voto creado correctamente"; break;
        }
        return new ResponseEntity<>(res, num == 2 ? HttpStatus.CREATED : HttpStatus.OK);
    }

    @GetMapping("findVotesOfUser")
    public ResponseEntity<?> findVotesOfUser(@Valid @RequestBody Long Id){
        return new ResponseEntity<>(voteRepository.findByUserId(Id), HttpStatus.OK);
    }
}