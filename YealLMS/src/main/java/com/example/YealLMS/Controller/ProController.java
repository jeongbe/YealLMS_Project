package com.example.YealLMS.Controller;

import com.example.YealLMS.Entity.Join.Professor;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/pro")
public class ProController {
    
//    //강의 코드 기준으로 수업계획서 페이지
//    @GetMapping("/syllabus/{lec_code}")
//    public String Syllabuspage(HttpSession session, Model model){
//
//        Professor professor = (Professor) session.getAttribute("professor");
////        log.info(professor.toString());
//        model.addAttribute("professor", professor);
//
//        return "syllabus";
//    }

    //공지사항 글쓰기
    @GetMapping("/insert/ann")
    public String insertAnn(HttpSession session,Model model){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

        return "PInsertAnn";
    }

    
    //교수 공지사항 메인 페이지
    @GetMapping("/ann")
    public String PAnnPage(HttpSession session,Model model){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

        return "ann/PAnnpage";
    }
    
}
