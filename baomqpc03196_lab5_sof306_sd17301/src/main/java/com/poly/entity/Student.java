package com.poly.entity;

import jakarta.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Students")
public class Student {
    @Id
    String email;
    String fullname;
    Double marks;
    Boolean gender;
    String country;
}
