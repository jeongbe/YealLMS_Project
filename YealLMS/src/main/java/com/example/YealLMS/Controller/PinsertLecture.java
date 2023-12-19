package com.example.YealLMS.Controller;

import com.example.YealLMS.DTO.LectureHeaderForm;
import com.example.YealLMS.Entity.LectureHeader;
import com.example.YealLMS.Entity.LectureList;
import com.example.YealLMS.Repository.LectureListRepository;
import com.example.YealLMS.Repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/pro")
public class PinsertLecture {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureListRepository lectureListRepository;

    @GetMapping("/insert")
    public String test(){

        return "professor/PinsertLecture";
    }

    //교수 진행중인 강의 페이지
    @GetMapping("/lectureList")
    public String lecList(Model model){

        List<LectureList> lel = lectureListRepository.getLeList();
        log.info(lel.toString());

        model.addAttribute("leList",lel);

        return "professor/Plecture";
    }

    //강의 헤더 정보 DB에 저장
    @PostMapping("/insert/lecture")
    public String insertL(LectureHeaderForm lectureHeaderForm){

        log.info("메핑 테스트");
//        log.info(lectureHeaderForm.toString());

        LectureHeader save = lectureHeaderForm.toEntity();
//        log.info(save.toString());

        LectureHeader LHeader = lectureRepository.save(save);
//        log.info(LHeader.toString())/;

        return "redirect:/pro/lectureList";
    }

    @GetMapping("/pmain")
    public String PMain(){

        return "professor/PMain";
    }
}
