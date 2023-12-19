package com.example.YealLMS.Entity.ProfessorEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class ProNams {

    @Id
    String pro_num;

    @Column
    String pro_name;

    @Column
    String pro_phone;
}
