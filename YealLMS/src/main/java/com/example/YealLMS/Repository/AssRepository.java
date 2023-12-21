package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.Ass.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssRepository  extends JpaRepository<Assignment, Integer> {

    @Query(value = "select a.*\n" +
            "from assignment a\n" +
            "join professor p \n" +
            "on a.pro_num = p.pro_num\n" +
            "where a.pro_num = :proNum" , nativeQuery = true)
    List<Assignment> AssList(@Param("proNum") String proNum);
}
