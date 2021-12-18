package com.informatorio.emprendimientos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Emprendimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede ser vacio")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "emprendimientoId", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Vote> votes = new ArrayList<>();
    private Integer votesCount = 0;

    @JoinTable(name = "events_emprendimientos",
            joinColumns = {@JoinColumn(name = "jt_emprendimiento",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "jt_event",nullable = false)}
    )
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private List<Event> events;

    private String description;
    private String content;
    private double goal;

    private boolean published;
}
