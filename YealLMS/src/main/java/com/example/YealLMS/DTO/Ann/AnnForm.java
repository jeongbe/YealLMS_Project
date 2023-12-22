package com.example.YealLMS.DTO.Ann;

import com.example.YealLMS.Entity.Ann.Announcement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnForm {

    //공지번호
    Long NotNum;

    //강의코드
    int LecCode;

    //교수번호
    Long ProNum;

    //교수명
    String ProName;

    //공지제목
    String NotTitle;

    //공지내용
    String NotContent;

    //공지 파일,이미지
    MultipartFile NotFile;

    //등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate NotDate;

    //조회수
    int NotCnt;

    public Announcement toEntity(){

        return new Announcement(NotNum,LecCode,ProNum,ProName,NotTitle,NotContent,NotFile != null ?
                NotFile.getOriginalFilename() : null,NotDate,NotCnt);
    }

}
