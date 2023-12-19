package com.example.YealLMS.Entity.ProfessorEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureList {

    @Id
    @Column(name = "lec_code")
    int lec_code;

    @Column(name = "pro_num")
    long pro_num;

    @Column
    (name = "lec_name")
    String lec_name;

    @Temporal(TemporalType.DATE)
    @Column
    (name = "let_start")
    Date let_start;

    @Temporal(TemporalType.DATE)
    @Column
    (name = "let_close")
    Date let_close;
}
