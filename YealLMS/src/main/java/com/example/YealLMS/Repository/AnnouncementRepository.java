package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.Ann.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    @Query(value = "select *\n" +
            "from ann\n" +
            "where lec_code= :lecCode\n" +
            "order by not_num desc", nativeQuery = true)
    List<Announcement> AllAnnList(@Param("lecCode") String leCode);


}
