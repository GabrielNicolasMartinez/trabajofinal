package com.informatorio.emprendimientos.service;

import com.informatorio.emprendimientos.dto.OperationVote;
import com.informatorio.emprendimientos.entity.Vote;
import com.informatorio.emprendimientos.repository.EmprendimientoRepository;
import com.informatorio.emprendimientos.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VoteService {

    private final VoteRepository votoRepo;
    private final EmprendimientoRepository empRepo;

    public VoteService(VoteRepository voteRepo, EmprendimientoRepository empRepo) {
        this.votoRepo = voteRepo;
        this.empRepo = empRepo;
    }

    public int create(OperationVote opv){
        var v = new Vote();
        v.setUserId(opv.getUserId());
        v.setEmprendimientoId(opv.getEmprendimientoId());
        v.setDate(LocalDateTime.now());
        var votes = votoRepo.findAll();
        var emp = empRepo.findById(opv.getEmprendimientoId());
        if(!emp.isPresent()) {
            return 0;
        }
        if(votes.stream().anyMatch(vo -> vo.getUserId().equals(v.getUserId())
                && vo.getEmprendimientoId().equals(v.getEmprendimientoId()))){
            return 1;
        }
        emp.get().setVotesCount(emp.get().getVotesCount() + 1);
        votoRepo.save(v);
        return 2;
    }


}