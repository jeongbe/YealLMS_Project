package com.example.YealLMS.DTO;

import com.example.YealLMS.Entity.LectureDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureDetailForm {

    int DelecCode;

    int LecCode;

    Long StuNum;

    int LecWeek;

    MultipartFile LecVideo;

    String LecTitle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date LecClose;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date LecStart;

    String LecCheck;

    String LecCount;

    String LecTask;

    public LectureDetail toEntity(){
        return new LectureDetail(DelecCode,LecCode,StuNum,LecWeek,
                LecVideo != null ? LecVideo.getOriginalFilename() : null,
                LecTitle,LecClose,LecStart,LecCheck,LecCount,LecTask);
    }
}
