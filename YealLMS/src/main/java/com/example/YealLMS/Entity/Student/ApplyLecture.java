package com.example.YealLMS.Entity.Student;

import com.example.YealLMS.Entity.LectureHeader;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "apply_lec")
public class ApplyLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long app_num;

    @Column
    int lec_code;

    @Column
    Long stu_num;


}
