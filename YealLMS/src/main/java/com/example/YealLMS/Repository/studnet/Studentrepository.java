package com.example.YealLMS.Repository.studnet;

import com.example.YealLMS.Entity.Join.student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Studentrepository extends CrudRepository<student,Long> {

  //학번 인증 쿼리
  @Query(value = "SELECT stu_num\n" +
          "FROM student\n" +
          "WHERE stu_num = :stu_num " +
          "And stu_birth = :stu_birth ", nativeQuery = true)

    Long certi(@Param("stu_num") Long stu_num, @Param("stu_birth") String stu_birth);

  //로그인 쿼리

  @Query(value = "SELECT *\n" +
          "FROM student\n" +
          "WHERE stu_num = :stu_num " +
          "AND stu_password = :stu_pass ", nativeQuery = true)

  student login(@Param("stu_num") Long stu_num, @Param("stu_pass") String stu_pass);


  //비밀번호 확인 쿼리
  @Query(value = "SELECT stu_num\n" +
          "FROM student\n" +
          "WHERE stu_num = :stu_num " +
          "AND stu_password = :stu_pass ", nativeQuery = true)

  Long chpass(@Param("stu_num") Long stu_num, @Param("stu_pass") String stu_pass);


}
