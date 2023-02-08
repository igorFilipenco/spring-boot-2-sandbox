package com.springboot2.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
@RequiredArgsConstructor
@Table(name = "to_do")
public class ToDo {
    @Id
    private Long id;
    @Version
    private Long version;

    @Column(value = "description")
    @NotNull
    @NotBlank
    private String description;

    @Column(value = "created")
    @CreatedDate
    private Timestamp created;

    @Column(value = "modified")
    @LastModifiedDate
    private Timestamp modified;

    @Column(value = "completed")
    private boolean completed;

    public boolean getCompleted() {
        return this.completed;
    }
}
