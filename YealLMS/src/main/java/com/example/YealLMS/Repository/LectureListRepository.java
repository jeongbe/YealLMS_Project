package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.ProfessorEntity.LectureList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureListRepository extends JpaRepository<LectureList,Integer> {

    @Query(value = "select l.lec_code, p.pro_num, l.lec_name, l.let_start, l.let_close\n" +
            "from lheader l\n" +
            "join professor p\n" +
            "on l.pro_num = p.pro_num\n" +
            "where l.pro_num = :pro_num", nativeQuery = true)
    List<LectureList> getLeList(@Param("pro_num") Long pro_num);
}
