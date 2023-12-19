package com.example.YealLMS.Repository.studnet;

import com.example.YealLMS.Entity.Join.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository  extends CrudRepository<Professor, Long> {

    @Query(value = "SELECT *\n" +
            "FROM professor\n" +
            "WHERE pro_num = 119981230\n" +
            "AND pro_password = 'yyy';", nativeQuery = true)
    Professor prologin(Long pro_num, String pro_pass);
}
