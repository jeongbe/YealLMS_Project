package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.Ass.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AssRepository  extends JpaRepository<Assignment, Integer> {

    @Query(value = "select a.*\n" +
            "from assignment a\n" +
            "join professor p \n" +
            "on a.pro_num = p.pro_num\n" +
            "where a.pro_num = :proNum" , nativeQuery = true)
    List<Assignment> AssList(@Param("proNum") String proNum);
    
        //세부강의까지 일치하는 과제정보가져오기
    @Query(value = "SELECT *\n" +
            "FROM assignment\n" +
            "WHERE lec_code = :leccode\n" +
            "AND delec_code = :deleccode ", nativeQuery = true)
    Assignment task(@Param("leccode") int leccode, @Param("deleccode") int deleccode);


    //전체 강의에서의 과제정보를 가져오기
    @Query(value = "SELECT *\n" +
            "FROM assignment\n" +
            "WHERE lec_code = :leccode\n" , nativeQuery = true)
    ArrayList<Assignment> tasklist(@Param("leccode") int leccode);

    @Query(value = "select ass_perfect\n" +
            "from assignment\n" +
            "where ass_num = :assNum", nativeQuery = true)
    Integer getPerfect(@Param("assNum") int assNum);

    //과제 채점한 학생수 구하는 ㅜ커리
    @Query(value = "select count(*)\n" +
            "from subtask\n" +
            "where ass_num = :assNum\n" +
            "and task_score between 1 and 10", nativeQuery = true)
    Integer getG(@Param("assNum") int assNum);
}
