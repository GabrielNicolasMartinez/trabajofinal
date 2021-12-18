package com.informatorio.emprendimientos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.informatorio.emprendimientos.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class OperationEmprendimiento {

    @NotEmpty(message = "El nombre no puede ser vacio")
    private String name;

    @NotEmpty(message = "El descripcion no puede ser vacio")
    private String description;
    private String content;
    private double goal;

    @NotNull
    @Positive
    @JsonProperty(value = "user_id")
    private Long userId;

    private List<String> tags;
    private boolean published = true;
}
