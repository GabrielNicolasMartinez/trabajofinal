package com.informatorio.emprendimientos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OperationVote {
    private Long userId;
    private Long emprendimientoId;
    private LocalDateTime date;
    private boolean voted;
}
