package com.chriniko.example.customers.domain;

import com.chriniko.example.customers.control.SurnameOnlyLettersAndNums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String firstname;

    private String initials;

    @NotEmpty
    @NotNull
    @SurnameOnlyLettersAndNums
    private String surname;

    @Version
    private long version;
}
