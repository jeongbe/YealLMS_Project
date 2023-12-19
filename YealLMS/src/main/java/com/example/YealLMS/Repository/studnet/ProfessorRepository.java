package com.example.YealLMS.Repository.studnet;

import com.example.YealLMS.Entity.Join.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorRepository  extends CrudRepository<Professor, Long> {

    @Query(value = "SELECT *\n" +
            "FROM professor\n" +
            "WHERE pro_num = :pro_num\n" +
            "AND pro_password = :pro_pass ", nativeQuery = true)
    Professor prologin(@Param("pro_num") Long pro_num, @Param("pro_pass") String pro_pass);

    @Query(value = "select *\n" +
            "from professor", nativeQuery = true)
    List<Professor> ProNameList();
}
