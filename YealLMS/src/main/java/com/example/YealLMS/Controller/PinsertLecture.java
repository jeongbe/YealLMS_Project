package com.example.YealLMS.Controller;

import com.example.YealLMS.DTO.LectureDetailForm;
import com.example.YealLMS.DTO.LectureHeaderForm;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Entity.LectureDetail;
import com.example.YealLMS.Entity.LectureHeader;
import com.example.YealLMS.Entity.ProfessorEntity.LectureList;
import com.example.YealLMS.Entity.ProfessorEntity.ProNams;
import com.example.YealLMS.Repository.LectureDetailRepository;
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
public class PinsertLecture {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureListRepository lectureListRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    ProNamsRepository proNamsRepository;

    @Autowired
    LectureDetailRepository lectureDetailRepository;

    //강의 헤더 추가 부분
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

    //안쓰는거
    @GetMapping("/pmain")
    public String PMain(HttpSession session,Model model){

        Professor professor = (Professor) session.getAttribute("professor");
        log.info(professor.toString());
        model.addAttribute("professor", professor);

        return "professor/PMain";
    }

    //강의 디테일 등록 페이지
    @GetMapping("/lectureList/DetailList/{lec_code}")
    public String DetailList(@PathVariable("lec_code")  String lecCode,Model model){

//        log.info(String.valueOf(lecCode));
        model.addAttribute("lecCode",lecCode);


        List<LectureDetail> LDList = lectureDetailRepository.LDitailList(lecCode);
        log.info(LDList.toString());

        model.addAttribute("LList", LDList);

        return "professor/PlectureD";
    }


    @GetMapping("/Detail/page/{lec_code}")
    public String DetailLectureInsert(Model model,@PathVariable("lec_code")  String lecCode){

//        log.info(String.valueOf(lecCode));
        model.addAttribute("lecCode",lecCode);



        return "professor/PlectureDInfo";
    }


    @PostMapping("/insert/detail/{lec_code}")
    public String DInsert(Model model, @PathVariable("lec_code")  String lecCode, LectureDetailForm form,
                          @RequestParam(value = "LecVideo",required = false)MultipartFile file1){

        log.info(String.valueOf(lecCode));
        model.addAttribute("lecCode",lecCode);

        log.info("file1 이름 : " + file1.toString());

//        log.info("메핑 연결");

        String link = "\\\\192.168.2.3\\images\\a";

        try {
            if(file1 != null){
                String vio1 = link + File.separator + file1.getOriginalFilename();
                Path filePath = Paths.get(vio1);
                Files.copy(file1.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e) {
            log.error("Error occurred while copying the file: {}", e.getMessage());
            e.printStackTrace();
            return "";
        }

        log.info(form.toString());

        LectureDetail test = form.toEntity();
        log.info(test.toString());
        //큰강의 코드 넣어줌
        test.setLec_code(Integer.parseInt(lecCode));
        test.setLec_check("미결");
        test.setLec_task("없음");  //소강의 당 과제 유뮤 확인
        lectureDetailRepository.save(test);


        return "redirect:/pro/lectureList/DetailList/" + lecCode;
    }

    //동영상 띄우기
    @GetMapping("/video/{lec_code}/{lec_week}")
    public String viewVideo(Model model,@PathVariable("lec_week")  String lecWeek,@PathVariable("lec_code")  String lecCode){

        LectureDetail showView = lectureDetailRepository.showView(lecCode,lecWeek);
        log.info(showView.toString());

        model.addAttribute("video",showView);

        return "professor/IFrame";
    }

}
