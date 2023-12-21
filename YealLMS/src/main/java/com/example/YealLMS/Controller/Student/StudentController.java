package com.example.YealLMS.Controller.Student;

import com.example.YealLMS.DTO.SearchForm;
import com.example.YealLMS.DTO.Student.ApplyForm;
import com.example.YealLMS.DTO.Student.ChangePassForm;
import com.example.YealLMS.DTO.Student.MyinfoChageForm;
import com.example.YealLMS.Entity.Join.student;
import com.example.YealLMS.Entity.LectureDetail;
import com.example.YealLMS.Entity.LectureHeader;
import com.example.YealLMS.Entity.Student.ApplyLecture;
import com.example.YealLMS.Repository.LectureDetailRepository;
import com.example.YealLMS.Repository.LectureRepository;
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
    public String stuinfo(@PathVariable Long stunum, HttpSession session, Model model){
        //로그인 세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        return "student/stuInfo";
    }

    //정보를 변경하는페이지 (세션전달)
    @GetMapping("/student/login/main/student/info/change/{stunum}")
    public String chinfo(@PathVariable Long stunum,HttpSession session, Model model){

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
    public String changePass(@PathVariable Long stunum,HttpSession session, Model model){

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
    public String lecdetail(HttpSession session, Model model, @PathVariable int leccode){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        //강의에 대한 세부정보 가져오기
        ArrayList<LectureDetail> lectureDetailArrayList = lectureDetailRepository.deleclist(leccode);
        //모델
        model.addAttribute("lectureDetailArrayList",lectureDetailArrayList);

        return "student/delec";
    }


}
