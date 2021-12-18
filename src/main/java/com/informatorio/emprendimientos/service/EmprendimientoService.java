package com.informatorio.emprendimientos.service;

import com.informatorio.emprendimientos.dto.OperationEmprendimiento;
import com.informatorio.emprendimientos.entity.Emprendimiento;
import com.informatorio.emprendimientos.entity.Tag;
import com.informatorio.emprendimientos.entity.User;
import com.informatorio.emprendimientos.repository.EmprendimientoRepository;
import com.informatorio.emprendimientos.repository.TagRepository;
import com.informatorio.emprendimientos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprendimientoService {

    private final EmprendimientoRepository empRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;

    @Autowired
    public EmprendimientoService(EmprendimientoRepository empRepo,
                                 UserRepository userRepo,
                                 TagRepository tagRepo) {
        this.empRepo = empRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
    }

    public Emprendimiento create(OperationEmprendimiento opEmp) {
        User user = userRepo.findById(opEmp.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario No Encontrado"));
        List<Tag> tags = tagRepo.findAll();
        var found = tags.stream().filter(t -> opEmp.getTags()
                .stream().anyMatch(o -> t.getName().contains(o))).collect(Collectors.toList());
        var nfound = opEmp.getTags().stream().filter(o -> !tags
                .stream().anyMatch(t -> t.getName().contains(o))).collect(Collectors.toList());
        nfound.forEach(nf -> {
                var t = new Tag(nf);
                found.add(t);
                tagRepo.save(t);
        });
        Emprendimiento emp = new Emprendimiento();
        emp.setName(opEmp.getName());
        emp.setDescription(opEmp.getDescription());
        emp.setOwner(user);
        emp.getTags().addAll(found);
        emp.setPublished(opEmp.isPublished());

        return empRepo.save(emp);
    }

    public Emprendimiento update(Emprendimiento emp, OperationEmprendimiento opemp){
        emp.setName(opemp.getName());
        emp.setDescription(opemp.getDescription());
        emp.setContent(opemp.getContent());
        emp.setGoal(opemp.getGoal());
        var tags = emp.getTags();
        var found = tags.stream().filter(t -> opemp.getTags()
                .stream().anyMatch(o -> t.getName().contains(o))).collect(Collectors.toList());
        var nfound = opemp.getTags().stream().filter(o -> !tags
                .stream().anyMatch(t -> t.getName().contains(o))).collect(Collectors.toList());
        nfound.forEach(nf -> {
            var t = new Tag(nf);
            found.add(t);
            tagRepo.save(t);
        });
        emp.setTags(found);
        return emp;
    }
}
