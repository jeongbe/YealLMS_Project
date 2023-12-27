package com.example.YealLMS.DTO.Ass;

import com.example.YealLMS.Entity.Ass.SubAss;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
public class SubAssForm {
    int subnum;

    int tasknum;

    Long stunum;

    String subcontent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date subdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date appdate;

    int taskscore;

    MultipartFile subfile;

    int asscnt = 1;

    String asscheck;


    public SubAssForm() {
        // subdate를 현재 날짜로 초기화
        this.subdate = new Date();

        // appdate를 0000-00-00으로 초기화
        this.appdate = new Date(0); // 또는 기타 방법으로 초기화 가능
    }




    public SubAss toEntity(){

        return new SubAss(subnum, tasknum, stunum, subcontent, subdate, appdate,taskscore, subfile.getOriginalFilename(),asscnt,asscheck);
    }

}
