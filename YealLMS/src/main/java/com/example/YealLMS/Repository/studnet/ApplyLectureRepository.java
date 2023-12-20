package com.example.YealLMS.Repository.studnet;

import com.example.YealLMS.Entity.Student.ApplyLecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ApplyLectureRepository extends CrudRepository<ApplyLecture, Long> {

    //수강신청한 리스트 가져오기
    @Query(value = "SELECT *\n" +
            "FROM apply_lec\n" +
            "WHERE stu_num = :stunum " ,nativeQuery = true)
   ArrayList<ApplyLecture> applylist(@Param("stunum") Long stunum);

    //수강신청한 정보에 따른 학번가져오기
    @Query(value = "SELECT stu_num\n" +
            "FROM apply_lec\n" +
            "WHERE lec_code = :leccode\n", nativeQuery = true)

    Long bringstu(@Param("leccode") int leccode);


}
