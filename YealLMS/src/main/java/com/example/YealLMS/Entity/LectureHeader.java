package com.example.YealLMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lheader")
public class LectureHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lec_code;

    //교수번호
    @JoinColumn(name = "pro_num")
    @Column
    int pro_num;

    //교수명
    String pro_name;

    //과목명
    String lec_name;

    //연락처
    String pro_phone;

    //학점
    int lec_credit;

    //수업목표
    String lec_purpose;

    //강의 시작일
    @Temporal(TemporalType.DATE)
    Date let_start;


    //강의 종료일
    @Temporal(TemporalType.DATE)
    Date let_close;

    //신청 시작일
    @Temporal(TemporalType.DATE)
    Date apply_start;

    //신청 마감일
    @Temporal(TemporalType.DATE)
    Date apply_close;

    //학생수
    int s_cnt;

    //중간고사
    String app_middle;

    //기말고사
    String app_last;

    //과제
    String app_task;

    //출석
    String app_check;

    //추천수
    int lec_reco;

}
