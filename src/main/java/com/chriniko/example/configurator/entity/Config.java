package com.chriniko.example.configurator.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode(of = {"id"})
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


@Entity
public class Config {

    @Id
    private String id;

    @Column(unique = true)
    private String label;

    private String value;

}
