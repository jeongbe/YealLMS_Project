package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.LectureHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface LectureRepository extends JpaRepository<LectureHeader,Integer> {

    //신청 마감일이 지나지 않은 강의만 불러온다
    @Query(value = "SELECT *\n" +
            "FROM lheader\n" +
            "WHERE apply_close > now() ",nativeQuery = true)
    ArrayList<LectureHeader> lecapp();

    @Query(value = "SELECT *\n" +
            "FROM lheader\n" +
            "WHERE lec_code = :leccode " , nativeQuery = true)
    ArrayList<LectureHeader> leclist(@Param("leccode") int leccode);

    //강의 코드 기준으로 학점 가져오기
    @Query(value = "SELECT lec_credit\n" +
            "FROM lheader\n" +
            "WHERE lec_code = :leccode " ,nativeQuery = true)
    int brcredit(@Param("leccode") int leccode);

    //검색기능
    @Query(value = "SELECT *\n" +
            "FROM lheader\n" +
            "WHERE apply_close > now()\n" +
            "AND ( lec_name LIKE %:searchname% or pro_name LIKE %:searchname%) ", nativeQuery = true)
    ArrayList<LectureHeader> searchlist(@Param("searchname") String searchname);

	
    @Query(value = "select count(*)\n" +
            "from lheader h\n" +
            "join apply_lec a\n" +
            "on h.lec_code = a.lec_code\n" +
            "where h.lec_code = :lecCode" , nativeQuery = true)
    int StuCount(@Param("lecCode") int lecCode);
}
