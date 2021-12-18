package com.informatorio.emprendimientos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

import lombok.*;

@Entity
@Where(clause = "active = true")
@SQLDelete(sql = "UPDATE event SET active=false WHERE id = ?")
@Setter
@Getter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;
    private String details;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate created;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    @Column(name="status", nullable = false, columnDefinition = "varchar(32) default 'OPEN'")
    @Enumerated(value = EnumType.STRING)
    private StatusType status = StatusType.OPEN;

    @ManyToMany(mappedBy = "events")
    @JsonIgnoreProperties({"description","content","created","goal","published","tags" })
    @OrderBy("votesCount DESC")
    private List<Emprendimiento> emprendimientos;

    private Double winnerReward;

    private boolean active = true;
}