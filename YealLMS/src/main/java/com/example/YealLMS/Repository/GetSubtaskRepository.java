package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.GetSubtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GetSubtaskRepository extends JpaRepository<GetSubtask, Long> {

    @Query(value = "select st.stu_num, st.stu_name, su.ass_check, su.sub_date, su.app_date,su.task_score, su.sub_num\n" +
            "from subtask su\n" +
            "join student st\n" +
            "on su.stu_num = st.stu_num\n" +
            "where ass_num = :assNum", nativeQuery = true)
    List<GetSubtask> getSubList(@Param("assNum") String assNum);
}
