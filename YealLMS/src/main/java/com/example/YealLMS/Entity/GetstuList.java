package com.example.YealLMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class GetstuList {

    @Id
    @Column(name = "stu_num")
    Long stu_num;

    @Column(name = "stu_name")
    String stu_name;

    @Column(name = "lec_name")
    String lec_name;

}
