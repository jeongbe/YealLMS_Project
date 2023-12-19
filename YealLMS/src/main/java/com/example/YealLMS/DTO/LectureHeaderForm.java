package com.example.YealLMS.DTO;

import com.example.YealLMS.Entity.LectureHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureHeaderForm {

    //강의코드
    int LecCode;

    //교수번호
    int ProNum;

    //교수명
    String ProName;

    //과목명
    String LecName;

    //연락처
    String ProPhone;

    //학점
    int LecCredit;

    //수업목표
    String LecPurpose;

    //강의 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date LetStart;

    //강의 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date LetClose;

    //신청 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ApplyStart;

    //신청 마감일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ApplyClose;

    //학생수
    int SCnt;

    //중간고사
    String AppMiddle;

    //기말고사
    String AppLast;

    //과제
    String AppTask;

    //출석
    String AppCheck;

    //추천수
    int LecReco;


    public LectureHeader toEntity(){
        return new LectureHeader(LecCode,ProNum,ProName,LecName,ProPhone,LecCredit,LecPurpose,LetStart,LetClose,
                ApplyStart,ApplyClose,SCnt,AppMiddle,AppLast,AppTask,AppCheck,LecReco);
    }
}
