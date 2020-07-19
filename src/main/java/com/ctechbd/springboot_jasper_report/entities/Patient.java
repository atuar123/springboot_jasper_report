package com.ctechbd.springboot_jasper_report.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name is Mandatory")
    private String name;
    private Integer age;
    private String gender;
    @NotBlank(message = "Mobile Number is Mandatory")
    private String mobileNo;
    private Date registerDate= new Date();
}

