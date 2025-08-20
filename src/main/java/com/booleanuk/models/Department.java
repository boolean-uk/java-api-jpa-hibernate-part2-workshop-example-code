package com.booleanuk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"department"})
    private List<Employee> employees;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
    // All args constructor and id constructor may be needed later

    public Department(int id) {
        this.id = id;
    }
}
