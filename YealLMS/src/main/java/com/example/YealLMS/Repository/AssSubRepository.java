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
}
