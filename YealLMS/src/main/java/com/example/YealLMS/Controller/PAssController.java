package com.example.YealLMS.Controller;

import com.example.YealLMS.DTO.Ass.AssForm;
import com.example.YealLMS.Entity.Ass.Assignment;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Entity.LectureDetail;
import com.example.YealLMS.Repository.AssRepository;
import com.example.YealLMS.Repository.LectureDetailRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/pro")
public class PAssController {

    @Autowired
    LectureDetailRepository lectureDetailRepository;

    @Autowired
    AssRepository assRepository;

    //교수가 과제 낼때 페이지
    @GetMapping("create/ass/{lec_code}/{delec_code}")
    public String InsertAss(HttpSession session, Model model, @PathVariable("delec_code")  String delecCode){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);
//        log.info(assForm.toString());

        LectureDetail lectureDetail = lectureDetailRepository.showLecWeek(delecCode);
        model.addAttribute("getWeek",lectureDetail);
//        log.info(lectureDetail.toString());


        return "professor/Pinsertass";
    }

    //과제 등록
    @PostMapping("/ass/data")
    public String  InsertAssData(Model model,HttpSession session,AssForm assForm, @RequestParam(value = "AssFile",required = false)MultipartFile file1){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

        log.info(assForm.toString());
        log.info(file1.toString());

        String link = "\\\\192.168.2.3\\images\\a";

        try {
            if(file1 != null && !file1.isEmpty()){
                String vio1 = link + File.separator + file1.getOriginalFilename();
                Path filePath = Paths.get(vio1);
                Files.copy(file1.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e) {
            log.error("Error occurred while copying the file: {}", e.getMessage());
            e.printStackTrace();
            return "";
        }

        Assignment savdDB = assForm.toEntity();
        savdDB.setDelec_code(assForm.getDelecCode());
        savdDB.setPro_num(professor.getPro_num());
        Assignment v = assRepository.save(savdDB);

        return "redirect:/pro/lectureList/DetailList/" + assForm.getLecCode();
    }

    //과제 목록 페이지
    @GetMapping("/asslist/{pro_num}")
    public String AssListpage(Model model,HttpSession session,@PathVariable("pro_num")  String proNum){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

        log.info(proNum);

        List<Assignment> AssList = assRepository.AssList(proNum);
        log.info(AssList.toString());
        model.addAttribute("AssList",AssList);

        return "Ass/PAssPage";
    }

    //과제 학생 제출 목록
    @GetMapping("/SAssList/{ass_num}")
    public String SAssList(Model model,HttpSession session,@PathVariable("ass_num")  String assNum){

        log.info(assNum);

        return "Ass/PAssList";
    }
}
