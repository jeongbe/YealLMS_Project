package com.example.YealLMS.Controller;

import com.example.YealLMS.DTO.Ass.AssForm;
import com.example.YealLMS.Entity.Ass.Assignment;
import com.example.YealLMS.Entity.Ass.SubAss;
import com.example.YealLMS.Entity.GetSubtask;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Entity.LectureDetail;
import com.example.YealLMS.Repository.AssRepository;
import com.example.YealLMS.Repository.AssSubRepository;
import com.example.YealLMS.Repository.GetSubtaskRepository;
import com.example.YealLMS.Repository.LectureDetailRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/pro")
public class PAssController {

    @Autowired
    LectureDetailRepository lectureDetailRepository;

    @Autowired
    AssRepository assRepository;

    @Autowired
    AssSubRepository assSubRepository;

    @Autowired
    GetSubtaskRepository getSubtaskRepository;

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

//        log.info(assForm.toString());
//        log.info(file1.toString());

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
    public String AssListpage(Model model, HttpSession session, @PathVariable("pro_num") String proNum) {
        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);

//        log.info(proNum);
        //제출 인원
        List<Integer> AssCont = new ArrayList<>();

        //평가 인원
        List<Integer> evaluation = new ArrayList<>();

        List<Assignment> AssList = assRepository.AssList(proNum);

        for (Assignment assignment : AssList) {
            int assNum = assignment.getAss_num();

            int submissionCount = assSubRepository.assCnt(assNum);
            AssCont.add(submissionCount);

            int evaluationCount = assRepository.getG(assNum);
            evaluation.add(evaluationCount);
        }

        model.addAttribute("AssCount", AssCont);

        model.addAttribute("eCount",evaluation);

//        log.info(AssList.toString());
        model.addAttribute("AssList", AssList);


//        log.info(AssCont.toString());

        return "Ass/PAssPage";
    }


    //과제 학생 제출 목록
    @GetMapping("/SAssList/{ass_num}")
    public String SAssList(Model model,HttpSession session,@PathVariable("ass_num")  String assNum){

        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);

//        log.info(assNum);
        List<GetSubtask> getSubtaskList = getSubtaskRepository.getSubList(assNum);
        log.info(getSubtaskList.toString());
        model.addAttribute("getsubList",getSubtaskList);

        //만점 점수 가져옴
        int PerfectNum = assRepository.getPerfect(Integer.parseInt(assNum));
        model.addAttribute("Pnum", PerfectNum);


        return "Ass/PAssList";
    }

    
    //학생당 과제 체점 페이지
    @GetMapping("/read/sub/{sub_num}")
    public String ReadSub(Model model,HttpSession session,@PathVariable("sub_num")  int subNum){

        Professor professor = (Professor) session.getAttribute("professor");
        model.addAttribute("professor", professor);

        SubAss subList = assSubRepository.oneSub(subNum);
//        log.info(subList.toString());
        model.addAttribute("subList",subList);

        int PerfectNum = assRepository.getPerfect(subList.getAss_num());
//        log.info(String.valueOf(PerfectNum));
        model.addAttribute("PerfectNum",PerfectNum);

        return "Ass/PsubScore";
    }

    @PostMapping("/grading/{sub_num}")
    public String Grading(Model model,@PathVariable("sub_num")  int subNum,@RequestParam("AssNum") int AssNum,
    @RequestParam("AssPerfect") int assP){
//        log.info(String.valueOf(assP));

        SubAss subList = assSubRepository.oneSub(subNum);
//        log.info(subList.toString());

        int assCnt = subList.getAss_cnt();
        assCnt = assCnt +1;

        subList.setTask_score(assP);
        assSubRepository.save(subList);

        return "redirect:/pro/SAssList/" + AssNum;
//        return null;

    }

}
