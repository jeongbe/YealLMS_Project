package com.example.YealLMS.Repository.Professor;

import com.example.YealLMS.Entity.ProfessorEntity.ProNams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProNamsRepository extends JpaRepository<ProNams,String > {

    @Query(value = "select pro_name\n" +
            "from professor", nativeQuery = true)
    List<ProNams> proNams();

    @Query(value = "select *\n" +
            "from professor p\n" +
            "where pro_num = :pronum" , nativeQuery = true)
    ProNams findInfo(@Param("pronum") Long pronum);
}
