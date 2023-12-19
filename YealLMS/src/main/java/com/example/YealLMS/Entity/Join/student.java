package com.example.YealLMS.Entity.Join;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class student {
    @Id
    Long stu_num;

    @Column
    String stu_password;

    @Column
    String stu_major;

    @Column
    int stu_grade;

    @Column
    String stu_name;

    @Column
    String stu_phone;

    @Column
    String stu_birth;

    @Column
    String stu_addr;

    @Column
    String stu_email;

    @Column
    String stu_blog;

    @Column
    String stu_intro;

    @Column
    int stu_credit;

    @Column
    String stu_image;

}
