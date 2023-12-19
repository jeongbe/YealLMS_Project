package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.LectureList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureListRepository extends JpaRepository<LectureList,Integer> {

    @Query(value = "select lec_code,lec_name,let_start,let_close\n" +
            "from lheader ", nativeQuery = true)
    List<LectureList> getLeList();
}
