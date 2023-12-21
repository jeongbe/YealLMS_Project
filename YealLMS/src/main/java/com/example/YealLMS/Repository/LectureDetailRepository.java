package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.LectureDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface LectureDetailRepository extends JpaRepository<LectureDetail,Integer> {

    @Query(value = "select *\n" +
            "from lecturedetail\n" +
            "where lec_code = :lecCode\n" +
            "order by lec_week asc" , nativeQuery = true)
    List<LectureDetail> LDitailList(@Param("lecCode") String lecCode);

    @Query(value = "select *\n" +
            "from lecturedetail\n" +
            "where lec_code=:lecCode \n" +
            "and lec_week = :lecWeek" , nativeQuery = true)
    LectureDetail showView(@Param("lecCode") String lecCode, @Param("lecWeek") String lecWeek);

    //강의 코드를 기준으로 세부강의 불러오기
    @Query(value = "SELECT *\n" +
            "FROM lecturedetail\n" +
            "WHERE lec_code = :leccode " , nativeQuery = true)
    ArrayList<LectureDetail> deleclist(@Param("leccode") int leccode);

    //동영상을 리스트로 가져오기
    @Query(value = "select *\n" +
            "from lecturedetail\n" +
            "where lec_code=:lecCode \n" +
            "and lec_week = :lecWeek" , nativeQuery = true)
    ArrayList<LectureDetail> showViewlist(@Param("lecCode") int lecCode, @Param("lecWeek") int lecWeek);

    @Query(value = "select *\n" +
            "from lecturedetail\n" +
            "where delec_code= :delecCode", nativeQuery = true)
    LectureDetail showLecWeek(@Param("delecCode") String delecCode);

}
