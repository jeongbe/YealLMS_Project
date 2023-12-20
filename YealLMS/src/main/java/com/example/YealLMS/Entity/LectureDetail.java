package com.example.YealLMS.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "lecturedetail")
public class LectureDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int delec_code;

    @Column
    @JoinColumn(name = "lec_code")
    int lec_code;

    @Column
    @JoinColumn(name = "stu_num")
    Long stu_num;

    @Column
    int lec_week;

    @Column
    String lec_video;

    @Column
    String lec_title;

    @Temporal(TemporalType.DATE)
    Date lec_close;

    @Temporal(TemporalType.DATE)
    Date lec_start;

    @Column
    String lec_check;

    @Column
    String lec_count;

    @Column
    String lec_task;
    public LectureDetail(int delecCode, int lecCode, Long stuNum, int lecWeek, String lecVideo, String lecTitle, Date lecClose, Date lecStart, String lecCheck, String lecCount
    ,String lecTask) {
        this.delec_code = delecCode;
        this.lec_code = lecCode;
        this.stu_num = stuNum;
        this.lec_week = lecWeek;
        this.lec_video = lecVideo;
        this.lec_title = lecTitle;
        this.lec_close = lecClose;
        this.lec_start = lecStart;
        this.lec_check = lecCheck;
        this.lec_count = lecCount;
        this.lec_task = lecTask;
    }

}
