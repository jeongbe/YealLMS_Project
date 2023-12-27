package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.Ann.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    @Query(value = "select *\n" +
            "from ann\n" +
            "where lec_code= :lecCode\n" +
            "order by not_num desc", nativeQuery = true)
    List<Announcement> AllAnnList(@Param("lecCode") String leCode);

    @Query(value = "select *\n" +
            "from ann\n" +
            "where lec_code= :lecCode\n" +
            "order by not_num desc", nativeQuery = true)
    ArrayList<Announcement> notList(@Param("lecCode") int leCode);

	    //교수가 작성한 모든 강의의 공지사항
    @Query(value = "select *\n" +
            "from ann\n" +
            "where pro_num=:proNum\n" +
            "order by not_date desc\n" +
            "limit 10", nativeQuery = true)
    List<Announcement> AnnList(@Param("proNum") Long proNum);
}
