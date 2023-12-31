package com.example.YealLMS.Controller.Student;

import com.example.YealLMS.DTO.Ass.SubAssForm;
import com.example.YealLMS.DTO.SearchForm;
import com.example.YealLMS.DTO.Student.ApplyForm;
import com.example.YealLMS.DTO.Student.ChangePassForm;
import com.example.YealLMS.DTO.Student.MyinfoChageForm;
import com.example.YealLMS.Entity.Ann.Announcement;
import com.example.YealLMS.Entity.Ass.Assignment;
import com.example.YealLMS.Entity.Ass.SubAss;
import com.example.YealLMS.Entity.Join.student;
import com.example.YealLMS.Entity.LectureDetail;
import com.example.YealLMS.Entity.LectureHeader;
import com.example.YealLMS.Entity.Student.ApplyLecture;
import com.example.YealLMS.Repository.*;
import com.example.YealLMS.Repository.studnet.ApplyLectureRepository;
import com.example.YealLMS.Repository.studnet.Studentrepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class StudentController {

    @Autowired
    Studentrepository studentrepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ApplyLectureRepository applyLectureRepository;
    @Autowired
    LectureDetailRepository lectureDetailRepository;
    @Autowired
    AssRepository assRepository;
    @Autowired
    AssSubRepository assSubRepository;
    @Autowired
    AnnouncementRepository announcementRepository;

    //메인페이지 세션전달
    @GetMapping("/student/login/main")
    public String seMain(HttpSession session, Model model){
        //로그인 세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        return "student/stuMain";
    }

    //나의 정보를 뿌려주는 페이지
    @GetMapping("/student/login/main/student/info/{stunum}")
    public String stuinfo(HttpSession session, Model model){
        //로그인 세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        return "student/stuInfo";
    }

    //정보를 변경하는페이지 (세션전달)
    @GetMapping("/student/login/main/student/info/change/{stunum}")
    public String chinfo(@PathVariable("stunum") Long stunum,HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);


        return "student/infochange";
    }

    //정보 변경 매핑
    @PostMapping("/student/login/main/student/info/")
    public String chmyinfo(MyinfoChageForm form, Model model, HttpSession session){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);


        //변경된 값이 있을때만 변경이 일어나도록 함
        if (form.getChphone() != ""){
            student.setStu_phone(form.getChphone());
        }
        if (form.getChaddr() != ""){
            student.setStu_addr(form.getChaddr());
        }
        if (form.getChmail() != ""){
            student.setStu_email(form.getChmail());
        }
        if (form.getChblog() != ""){
            student.setStu_blog(form.getChblog());
        }

        studentrepository.save(student);
        model.addAttribute("student", student);


        //다시 정보 뿌려주는페이지로 리다이렉트
        return "redirect:/student/login/main/student/info/" + student.getStu_num();

    }

    //비밀번호 변경하는페이지로 이동(세션전달)
    @GetMapping("/student/login/main/student/info/changePass/{stunum}")
    public String changePass(@PathVariable("stunum") Long stunum,HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);


        return "student/passchange";
    }

    //비밀번호를 변경하는 매핑
    @PostMapping("/student/login/main/student/info/chpass")
    public String changePass(Model model, HttpSession session, ChangePassForm form){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //비밀번호가 맞는지 확인하는과정
        Long check = studentrepository.chpass(student.getStu_num(),form.getStupassword());


        //비밀번호가 일치한다면 유저정보 가져와 업데이트
        if (check != null){

            student.setStu_password(form.getChpassword());
            studentrepository.save(student);

            return "student/stuInfo";
        }
        else {
            String error = "비밀번호가 맞는지 확인하세요";
            model.addAttribute("error",error);
            return "student/passchange";


        }

    }

    //강의 목록으로 가는 매핑
    @GetMapping("/student/login/main/student/info/leclist")
    public String leclist(HttpSession session, Model model){
        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

         //학생이 신청한 수강신청 정보 가져오기
         ArrayList<ApplyLecture> applyLecture = applyLectureRepository.applylist(student.getStu_num());

        // 수강신청 정보의 강의코드를 가져와서 강의 리스트를 뿌려줍니다.
        ArrayList<LectureHeader> lectureHeaderArrayList = new ArrayList<>();
        // 수강신청 정보도 여러개 뿌려줘야할 정보도 여러개 이므로 각각 인덱스마다 조회를 하기위해 for문으로 반복을 돌린다.
        for (ApplyLecture apply : applyLecture) {
            lectureHeaderArrayList.addAll(lectureRepository.leclist(apply.getLec_code()));
        }

        //정보를 모델화 하기
         model.addAttribute("lectureHeaderArrayList", lectureHeaderArrayList);


        return "student/leclist";
    }

    //강의 신청으로 가는 매핑

    @GetMapping("/student/login/main/lecapp")
    public String lecapp(HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //등록된 강의 정보 불러오기
        //신청마감일이 지나지 않은 강의만 불러옴
        ArrayList<LectureHeader> lectureHeaders = lectureRepository.lecapp();
        model.addAttribute("lectureHeaders", lectureHeaders);


        return "student/lecapp";
    }

    //검색하는 매핑
    @GetMapping("/student/login/main/lecapp/search")
    public String lecsearch(HttpSession session, Model model, SearchForm form){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //검색 내용에따라 해당하는 교수명or 과목명으로 조회하여 가져온다.
        //신청마감일이 지나지않은 검색된 데이터만 조회함
        ArrayList<LectureHeader> searchlec = lectureRepository.searchlist(form.getSearchname());
        model.addAttribute("lectureHeaders",searchlec);


        //아무검색어도 입력하지 않았을경우 그냥 페이지 새로고침이 된다
        if (form.getSearchname() == ""){
            return "redirect:/student/login/main/lecapp";
        }
        //검색창에 하나라도 입력했을시 검색조건 비교
        else{

        if (!searchlec.isEmpty()){

        return "student/lecapp";

        }
        else {

            String notfound = "일치하는 정보가 없습니다";
            model.addAttribute("notfound",notfound);

            return "redirect:/student/login/main/lecapp?notfound=true";
        }
        }
    }
    //커뮤니티로 가는 매핑
    @GetMapping("/student/login/main/allnot")
    public String allnot(HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        return "student/allnot";
    }
    //수강신청하는 매핑
    @GetMapping("/student/apply/lecture")
    public String applec(HttpSession session, Model model, ApplyForm form){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //이미 신청한 수강인지 가져오기
        ArrayList<ApplyLecture> stunum = applyLectureRepository.bringapp(student.getStu_num(),form.getLeccode());

        //수강한 신청이 아니라면 수강신청이 완료됨
        if (stunum.isEmpty()){
            
        //성공 메세지 띄어주기
        String sucess = "강의 신청이 완료되었습니다";
        model.addAttribute("sucess", sucess);

        //신청한 강의에 학점이 추가되어 업데이트 됨
            //현재 학생의 수강중이 학점 가져오기
            int credit = student.getStu_credit();
            //신청한 강의의 학점가져오기
            int leccredit = lectureRepository.brcredit(form.getLeccode());
            //수강중인 학생의 학점과 새로 신청한 학점을 더해주기
            int appcredit = credit + leccredit;

            //더해진 학점을 학생 정보로 업데이트
            //최대 수강 학점이 30학점 이므로 30을 넘어가면 취소되도록 if 작성
            if (appcredit<=30) {

                //엔티티로 변환후 수강신청 테이블에 저장
                ApplyLecture applyLecture = form.toEntity();
                applyLectureRepository.save(applyLecture);
                
                //추가된 학점으로 업데이트
                student.setStu_credit(appcredit);
                studentrepository.save(student);

                return "redirect:/student/login/main/student/info/leclist?sucess=true";
            }
            //최대 수강신청이 30을 넘는다면 띄어주는 오류 메세지
            else {
                String maxcredit = "최대 수강학점은 30입니다.";
                model.addAttribute("maxcredit", maxcredit);
                return "redirect:/student/login/main/lecapp?maxcredit=true";
            }
        }
        //이미 신청한 강의라면 에러메세지를 띄어줌
        else {
            String error = "이미 신청한 강의 입니다";
            model.addAttribute("error", error);

            return "redirect:/student/login/main/lecapp?error=true";

        }
    }
    //강의 디테일로가는 매핑
    @GetMapping("/student/login/main/student/info/leclist/{leccode}")
    public String lecdetail(HttpSession session, Model model, @PathVariable("leccode") int leccode){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);
        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //강의에 대한 세부정보 가져오기
        ArrayList<LectureDetail> lectureDetailArrayList = lectureDetailRepository.deleclist(leccode);
        log.info(lectureDetailArrayList.toString());


        // 과제 여부 판단하여 가져오기
        List<Assignment> assignmentList = new ArrayList<>();


         for (LectureDetail lectureDetail : lectureDetailArrayList) {

            // 각각의 강의 디테일 코드를 가져와서 assRepository.task 메소드에 전달
            int detailCode = lectureDetail.getDelec_code();
            Assignment assignment = assRepository.task(leccode, detailCode);

            // 과제가 있을 경우에만 리스트에 추가
            if (assignment != null) {
                assignmentList.add(assignment);
                model.addAttribute("assignmentList", assignmentList);
            }
        }

        model.addAttribute("lectureDetailArrayList",lectureDetailArrayList);

        return "student/delec";

    }

    //과제 목록 페이지로 가는 매핑
    @GetMapping("/student/login/main/student/info/tasklist/{leccode}")
    public String tasklist(@PathVariable("leccode") int leccode, HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //과제리스트 가져오기
        ArrayList<Assignment> assignmentArrayList = assRepository.tasklist(leccode);

        //모델
        model.addAttribute("assignmentArrayList",assignmentArrayList);

        //과제 제출 테이블에서 학번으로 조회하여 제출여부 판단하기
        ArrayList<Long> chtask = new ArrayList<>();
        for(Assignment check : assignmentArrayList) {
            chtask.addAll(assSubRepository.brstunum(student.getStu_num(),check.getAss_num()));
        }
        if (chtask != null){
            String subtask = "제출완료";
            model.addAttribute("subtask", subtask);
        }
        else {
            String subtaskno = "미제출";
            model.addAttribute("subtaskno", subtaskno);
        }


        return "student/tasklist";
    }

    //과제 디테일로가는 매핑assignment
    @GetMapping("/student/login/main/student/info/taskdetail/{leccode}/{assnum}")
    public String taskdetail(HttpSession session, Model model, @PathVariable("leccode") int leccode,@PathVariable("assnum") int assnum){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //과제코드로 해당과제 가져오기
        Assignment assignment = assRepository.findById(assnum).orElse(null);
        model.addAttribute("assignment",assignment);



        return "student/taskdetail";
    }

    //수업 계획서로 가는 매핑
    @GetMapping("/student/login/main/student/info/lecpur/{leccode}")
    public String lecpur(@PathVariable("leccode") int leccode, HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        return "student/lecpur";
    }

    //과제를 등록하는 페이지로 가는 매핑
    @GetMapping("/student/login/main/student/info/taskput/{leccode}/{assnum}")
    public String taskput(HttpSession session, Model model, @PathVariable("leccode") int leccode,@PathVariable("assnum") int assnum){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //과제세션
        Assignment assignment = assRepository.findById(assnum).orElse(null);
        model.addAttribute("assignment",assignment);



        return "student/taskput";
    }

    //학생이 과제를 제출할때 이루어지는 매핑
    @PostMapping("/student/login/main/student/info/subtask/{leccode}")
    public String subtask(HttpSession session, Model model, @PathVariable("leccode") int leccode,SubAssForm form,@RequestParam(value = "subfile",required = false) MultipartFile file1){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);


        //디폴트로 점수는 우선 0으로 세팅됨
        form.setTaskscore(0);

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

        //과제 제출 테이블로 저장과정
        form.setAsscheck("제출");

        SubAss subAss = form.toEntity();

        if ((subAss.getSub_file() == "") || (subAss.getSub_file() == null)){
            subAss.setSub_file("없음");

        }



         //과제 제출 인원이 생길때마다 1씩 추가
        SubAss asscnt = assSubRepository.findById(subAss.getAss_num()).orElse(null);

        if (asscnt != null){
         int cnt = asscnt.getAss_cnt();
         cnt++;
         subAss.setAss_cnt(cnt);
        }

        assSubRepository.save(subAss);

        return "redirect:/student/login/main/student/info/tasklist/" + leccode;
    }

    //공지사항으로 가는 매핑
    @GetMapping("/student/login/main/student/info/lecnot/{leccode}")
    public String lecnot(HttpSession session, @PathVariable("leccode") int leccode, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //강의코드에 해당하는 공지사항 가져오기
        ArrayList<Announcement> announcementArrayList = announcementRepository.notList(leccode);
        model.addAttribute("announcementArrayList",announcementArrayList);

        return "student/lecnot";
    }

    //공지사항 디테일로 가는 매핑
    @GetMapping("/student/login/main/student/info/lecdenot/{leccode}/{notnum}")
    public String lecdenot(HttpSession session, Model model, @PathVariable("leccode") int leccode, @PathVariable("notnum") Long notnum){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //공지 코드에 해당하는 공지 세부사항 가져오기
        Announcement announcement = announcementRepository.findById(notnum).orElse(null);
        model.addAttribute("announcement",announcement);

        return "student/lecdenot";
    }

    //수강중인 학생 현황
    @GetMapping("/student/login/main/student/info/stulist/{leccode}")
    public String stulist(HttpSession session, Model model, @PathVariable("leccode") int leccode){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //큰 강의코드 세션
        LectureHeader lectureHeader = lectureRepository.findById(leccode).orElse(null);
        model.addAttribute("lectureHeader",lectureHeader);

        //강의코드를 기준으로 학번 가져오기
        ArrayList<Long> stdnumlist = applyLectureRepository.brstd(leccode);

        //학번을 기준으로 학생정보 가져오기
        ArrayList<student> studentArrayList = new ArrayList<>();

       for (Long stdlist : stdnumlist){
           student student1 = studentrepository.findById(stdlist).orElse(null);

           if (student1 != null){
               studentArrayList.add(student1);
           }
       }

       model.addAttribute("studentArrayList",studentArrayList);



        return "student/lecstulist";
    }

}