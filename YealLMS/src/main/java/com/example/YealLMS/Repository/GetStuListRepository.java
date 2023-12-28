package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.GetstuList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GetStuListRepository extends JpaRepository<GetstuList,Long> {

    @Query(value = "select s.stu_name,lec_name,s.stu_num\n" +
            "from lheader l\n" +
            "join apply_lec a\n" +
            "on l.lec_code = a.lec_code\n" +
            "join student s\n" +
            "on s.stu_num = a.stu_num\n" +
            "where l.lec_code = :lecCode", nativeQuery = true)
    List<GetstuList> getstuList(@Param("lecCode") int lecCode);
}
