package com.example.YealLMS.Controller;

import com.example.YealLMS.Entity.GetstuList;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Entity.Join.student;
import com.example.YealLMS.Entity.LectureHeader;
import com.example.YealLMS.Entity.ProfessorEntity.LectureList;
import com.example.YealLMS.Repository.*;
import com.example.YealLMS.Repository.studnet.ApplyLectureRepository;
import com.example.YealLMS.Repository.studnet.ProfessorRepository;
import com.example.YealLMS.Repository.studnet.Studentrepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/pro")
public class ProController {

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    GetStuListRepository getStuListRepository;

    @Autowired
    LectureRepository lectureRepository;
    
    //교수 나의 정보 페이지
    @GetMapping("/info")
    public String ProInfoPage(HttpSession session, Model model){
        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);

        Optional<Professor> ProInfo = professorRepository.findById(professor.getPro_num());
        model.addAttribute("ProInfo", ProInfo);

        return "professor/ProInfo";
    }

    //수강생 리스트
    @GetMapping("/stulist/{lecCode}")
    public String stulist(HttpSession session, Model model, @PathVariable("lecCode") int leccode){

        model.addAttribute("lecCode",leccode);

        //세션
        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);


        List<GetstuList> getList = getStuListRepository.getstuList(leccode);
        log.info(getList.toString());
        model.addAttribute("List",getList);


        return "StdList";
    }

    //수업 계획서

    @GetMapping("/lecinfo/{lecCode}")
    public String lecInfo(HttpSession session, Model model, @PathVariable("lecCode") int leccode){

        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);

        LectureHeader lecInfo = lectureRepository.findById(leccode).orElse(null);
        log.info(lecInfo.toString());
        model.addAttribute("lecInfo",lecInfo);

        return "professor/Prolecpur";
    }
}
