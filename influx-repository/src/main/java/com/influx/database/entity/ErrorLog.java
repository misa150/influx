package com.influx.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "error_log", schema = "world")
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime errorDate;

    private String errorMessage;

}
