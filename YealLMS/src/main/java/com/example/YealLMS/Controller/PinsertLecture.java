package com.example.YealLMS.Controller;

import com.example.YealLMS.DTO.LectureHeaderForm;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Entity.LectureHeader;
import com.example.YealLMS.Entity.ProfessorEntity.LectureList;
import com.example.YealLMS.Entity.ProfessorEntity.ProNams;
import com.example.YealLMS.Repository.LectureListRepository;
import com.example.YealLMS.Repository.LectureRepository;
import com.example.YealLMS.Repository.Professor.ProNamsRepository;
import com.example.YealLMS.Repository.studnet.ProfessorRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/pro")
public class PinsertLecture {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureListRepository lectureListRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    ProNamsRepository proNamsRepository;

    @GetMapping("/insert")
    public String test(Model model,HttpSession session){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);
        log.info(professor.getPro_num().toString());

        ProNams proInfo = proNamsRepository.findInfo(professor.getPro_num());
        log.info(proInfo.toString());

        model.addAttribute("proName",proInfo.getPro_name());
        model.addAttribute("proPhone",proInfo.getPro_phone());

        return "professor/PinsertLecture";
    }

    //교수 진행중인 강의 페이지
    @GetMapping("/lectureList")
    public String lecList(Model model , HttpSession session){

        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);
//        log.info(String.valueOf(professor.getPro_num()));
        List<LectureList> lel = lectureListRepository.getLeList(professor.getPro_num());
//        log.info(lel.toString());

        model.addAttribute("leList",lel);

        return "professor/Plecture";
    }

    //강의 헤더 정보 DB에 저장
    @PostMapping("/insert/lecture")
    public String insertL(LectureHeaderForm lectureHeaderForm,Model model , HttpSession session){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

//        log.info("메핑 테스트");
//        log.info(lectureHeaderForm.toString());

        LectureHeader save = lectureHeaderForm.toEntity();
        save.setPro_num(Math.toIntExact(professor.getPro_num()));
//        log.info(save.toString());

        LectureHeader LHeader = lectureRepository.save(save);
//        log.info(LHeader.toString());

        return "redirect:/pro/lectureList";
    }

    @GetMapping("/pmain")
    public String PMain(HttpSession session,Model model){

        Professor professor = (Professor) session.getAttribute("professor");
        log.info(professor.toString());
        model.addAttribute("professor", professor);

        return "professor/PMain";
    }


}
