package com.example.YealLMS.DTO.Student;

import com.example.YealLMS.Entity.Student.ApplyLecture;
import lombok.Data;

@Data
public class ApplyForm {
    int leccode;
    Long stunum;

    public ApplyLecture toEntity(){

        return new ApplyLecture(null,leccode,stunum);
    }
}
