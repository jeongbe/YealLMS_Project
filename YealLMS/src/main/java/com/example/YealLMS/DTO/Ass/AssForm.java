package com.example.YealLMS.DTO.Ass;

import com.example.YealLMS.Entity.Ass.Assignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssForm {

    int AssNum;

    int LecCode;

    int DelecCode;

    int LecWeek;

    Long StuNum;

    Long ProNum;

    String AssTitle;

    String AssContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date AssStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date AssClose;

    int AssPerfect;

    int AsCnt;

    int AppCnt;

    MultipartFile AssFile;

    public Assignment toEntity(){

        return new Assignment(AssNum,LecCode,DelecCode,LecWeek,StuNum,ProNum,AssTitle,AssContent,
                AssStart,AssClose,AssPerfect,AsCnt,AppCnt,AssFile != null ? AssFile.getOriginalFilename() : null);
    }
}
