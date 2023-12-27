package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.Ass.SubAss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssSubRepository extends JpaRepository<SubAss,Integer> {
    @Query(value = "SELECT stu_num\n" +
            "FROM subtask\n" +
            "WHERE stu_num = :stunum ", nativeQuery = true)
    Long brstunum(@Param("stunum") Long stunum);

    //과제 제출 인원
    @Query(value = "select count(*)\n" +
            "from subtask\n" +
            "where ass_num = :assNum", nativeQuery = true)
    Integer assCnt(@Param("assNum") int assNum);

    @Query(value = "select *\n" +
            "from subtask s\n" +
            "where sub_num = :subNum\n" +
            "and sub_file IS NOT NULL", nativeQuery = true)
    SubAss oneSub(@Param("subNum") int subNum);


}
