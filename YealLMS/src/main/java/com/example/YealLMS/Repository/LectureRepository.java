package com.example.YealLMS.Repository;

import com.example.YealLMS.Entity.LectureHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<LectureHeader,Integer> {
}
