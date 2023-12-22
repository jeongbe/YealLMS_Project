package com.example.YealLMS.Controller;

import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Repository.studnet.ProfessorRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/pro")
public class ProController {

    @Autowired
    ProfessorRepository professorRepository;
    
    //교수 나의 정보 페이지
    @GetMapping("/info")
    public String ProInfoPage(HttpSession session, Model model){
        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);

        Optional<Professor> ProInfo = professorRepository.findById(professor.getPro_num());
        model.addAttribute("ProInfo", ProInfo);

        return "professor/ProInfo";
    }
}
