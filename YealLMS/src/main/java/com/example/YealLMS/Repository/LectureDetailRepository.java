package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.LectureDetail;
import jakarta.persistence.PrePersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
