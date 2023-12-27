package com.example.YealLMS.Entity.Ass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subtask")
public class SubAss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sub_num;
    @Column
    int task_num;
    @Column
    Long stu_num;
    @Column
    String sub_content;
    @Column
    Date sub_date;
    @Column
    Date app_date;
    @Column
    int task_score;
    @Column
    String sub_file;
    @Column
    int ass_cnt;
    @Column
    String ass_check;
}
