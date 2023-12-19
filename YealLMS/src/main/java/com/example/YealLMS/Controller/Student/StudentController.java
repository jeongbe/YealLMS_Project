package com.example.YealLMS.Controller.Student;

import com.example.YealLMS.DTO.Student.ChangePassForm;
import com.example.YealLMS.DTO.Student.MyinfoChageForm;
import com.example.YealLMS.Entity.Join.student;
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

@Controller
@Slf4j
public class StudentController {

    @Autowired
    Studentrepository studentrepository;

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


        return "student/leclist";
    }

    //강의 신청으로 가는 매핑

    @GetMapping("/student/login/main/lecapp")
    public String lecapp(HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);

        return "student/lecapp";
    }

    //커뮤니티로 가는 매핑
    @GetMapping("/student/login/main/allnot")
    public String allnot(HttpSession session, Model model){

        //세션
        student student = (student) session.getAttribute("student");
        model.addAttribute("student", student);


        return "student/allnot";
    }
}
