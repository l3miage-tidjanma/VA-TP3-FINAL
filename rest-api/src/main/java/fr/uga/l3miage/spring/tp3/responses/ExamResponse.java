package fr.uga.l3miage.spring.tp3.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExamResponse {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private int weight;
}
