package com.example.YealLMS.Entity.Ass;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ass_num;

    @Column
    int lec_code;

    @Column
    int delec_code;

    @Column
    int lec_week;

    @Column
    Long stu_num;

    @Column
    Long pro_num;

    // 과제 제목
    @Column
    String ass_title;

    //과제 내용
    @Column
    String ass_content;

    //과제 시작일
    @Temporal(TemporalType.DATE)
    Date ass_start;

    //과제 마감일
    @Temporal(TemporalType.DATE)
    Date ass_close;

    //과제 만점
    @Column
    int ass_perfect;

    //과제 제출 인원
    @Column
    int ass_cnt;

    //과제 평가 인원
    @Column
    int app_cnt;

    //첨부 파일
    @Column
    String ass_file;

}
