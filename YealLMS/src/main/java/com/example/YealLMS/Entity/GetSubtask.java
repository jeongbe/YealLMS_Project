package com.example.YealLMS.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class GetSubtask {

    @Id
    @Column(name = "sub_num")
    int sub_num;

    @Column(name = "stu_num")
    Long stu_num;

    @Column(name = "stu_name")
    String stu_name;

    @Column(name = "ass_check")
    String ass_check;

    @Column(name = "sub_date")
    String sub_date;

    @Column(name = "app_date")
    String app_date;

    @Column(name = "task_score")
    int task_score;


}
