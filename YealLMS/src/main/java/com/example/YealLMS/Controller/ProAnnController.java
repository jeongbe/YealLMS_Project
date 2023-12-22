package com.example.YealLMS.Controller;

import com.example.YealLMS.DTO.Ann.AnnForm;
import com.example.YealLMS.Entity.Ann.Announcement;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Repository.AnnouncementRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/pro")
public class ProAnnController {

    @Autowired
    AnnouncementRepository announcementRepository;


    //공지사항 글쓰기 페이지
    @GetMapping("/insert/ann/{lec_code}")
    public String insertAnn(@PathVariable("lec_code")  String lecCode,HttpSession session,Model model){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);
        model.addAttribute("lecCode",lecCode);
//        log.info(lecCode);


        return "ann/PInsertAnn";
    }

    
    //교수 공지사항 메인 페이지
    @GetMapping("/ann/{lec_code}")
    public String PAnnPage(@PathVariable("lec_code")  String lecCode,HttpSession session, Model model){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);
        model.addAttribute("lecCode",lecCode);

        List<Announcement> AnnList = announcementRepository.AllAnnList(lecCode);
//        if(AnnList != null){
//            log.info(AnnList.toString());
//        }
        model.addAttribute("AnnList",AnnList);

        return "ann/PAnnpage";
    }

    //공지 정보 DB로 저장
    @PostMapping("/ann/insert")
    public String InsertAnn(AnnForm form,HttpSession session, Model model){
        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

//        log.info(form.toString());

        String link = "\\\\192.168.2.3\\images\\a";

        MultipartFile file1 = form.getNotFile();

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

        Announcement target = form.toEntity();
        target.setPro_num(professor.getPro_num());
        target.setPro_name(professor.getPro_name());

        Announcement save = announcementRepository.save(target);


        return "redirect:/pro/ann/" + form.getLecCode();
    }

    //공지 읽기
    @GetMapping("/read/{not_num}")
    public String ReadAnn(HttpSession session, Model model,@PathVariable("not_num")  String notNum){

        Professor professor = (Professor) session.getAttribute("professor");
//        log.info(professor.toString());
        model.addAttribute("professor", professor);

        log.info(notNum);

        Optional<Announcement> oneAnn = announcementRepository.findById(Long.valueOf(notNum));

        log.info(oneAnn.toString());
        model.addAttribute("OneAnn",oneAnn);

        return "ann/AnnReadPage";
    }
    
}
