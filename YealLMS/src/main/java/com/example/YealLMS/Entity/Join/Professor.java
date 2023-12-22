package com.example.YealLMS.Entity.Join;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Controller;

@Entity
@Data
public class Professor {

    @Id
    Long pro_num;

    @Column
    String pro_password;

    @Column
    String pro_major;

    @Column
    String pro_name;

    @Column
    String pro_phone;

    @Column
    String pro_birth;

    @Column
    String pro_addr;

    @Column
    String pro_blog;

    @Column
    String pro_email;

    @Column
    String pro_room;

}
