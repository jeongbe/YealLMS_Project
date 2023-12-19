package com.example.YealLMS.Repository.studnet;

import com.example.YealLMS.Entity.Join.student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Studentrepository extends CrudRepository<student,Long> {

  //학번 인증 쿼리
  @Query(value = "SELECT stu_num\n" +
          "FROM student\n" +
          "WHERE stu_num = :stu_num " +
          "And stu_birth = :stu_birth ", nativeQuery = true)

    Long certi(Long stu_num, String stu_birth);

  //로그인 쿼리

  @Query(value = "SELECT *\n" +
          "FROM student\n" +
          "WHERE stu_num = :stu_num " +
          "AND stu_password = :stu_pass ", nativeQuery = true)

  student login(Long stu_num, String stu_pass);


  //비밀번호 확인 쿼리
  @Query(value = "SELECT stu_num\n" +
          "FROM student\n" +
          "WHERE stu_num = :stu_num " +
          "AND stu_password = :stu_pass ", nativeQuery = true)

  Long chpass(Long stu_num, String stu_pass);


}
