package com.example.YealLMS.Controller.join;

import com.example.YealLMS.DTO.JoinForm.StuCertiForm;
import com.example.YealLMS.DTO.JoinForm.StuJoinForm;
import com.example.YealLMS.Entity.Join.Professor;
import com.example.YealLMS.Entity.Join.student;
import com.example.YealLMS.Repository.studnet.ProfessorRepository;
import com.example.YealLMS.Repository.studnet.Studentrepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class certiController {

    @Autowired
    Studentrepository studentrepository;
    @Autowired
    ProfessorRepository professorRepository;


     //학번 인증 매핑
    @PostMapping("/student/certi")
    public String stucerti(StuCertiForm form, Model model){
        Long certi_num = studentrepository.certi(form.getStunum(),form.getStubirth());
        //입력한 정보들이 존재한다고 회원가입 페이지로 이동
        if (certi_num != null){
            model.addAttribute("certi",certi_num);
            return  "join,login/join";
        }
        //아니라면 확인문구 띄어줌
        else {
            String error = "학번 또는 생년월일을 확인하세요";
            model.addAttribute("error", error);
            return "join,login/Certi";
        }
    }


    //회원가입
    @PostMapping ("/student/join")
    public String stujoin(StuJoinForm form){

         student student = studentrepository.findById(form.getStunum()).orElse(null);

         //회원가입 하고자 하는 학번이 존재한다면 설정한 비밀번호로 업데이트 해준다
         if (student != null)
         {
             student.setStu_password(form.getStupass());
             studentrepository.save(student);
             return "join,login/login";
         }
         //오류시 처리
         else {
             return "";
         }

    }

    //로그인 매핑
    @PostMapping("/student/login/main")
    public String stulogin(StuJoinForm form, Model model,HttpServletRequest request){
        // 교수와 구분하여 정보가 일치하는지확인
        //학생이라면
        if (form.getStunum()<100000000){
            //학생 레포지토리에서 비교
         student student = studentrepository.login(form.getStunum(),form.getStupass());

            //정보가 일치한다면 세션 생성후 메인페이지 반환
            if (student != null){
                HttpSession session = request.getSession();
                session.setAttribute("student",student);
                model.addAttribute("student", student);
                return  "student/stuMain";
            }
            //정보가 일치하지 않는다면 에러메세지를 띄어줌
            else {
                String error ="학번또는 비밀번호를 확인하세요";
                model.addAttribute("error",error);
                return "join,login/login";

            }

        }
        else {
            //교수 레파진토리에서 비교
            Professor professor = professorRepository.prologin(form.getStunum(), form.getStupass());

            if (professor != null){
                HttpSession session = request.getSession();
                session.setAttribute("professor",professor);
                model.addAttribute("professor", professor);
                return "professor/PMain";
            }
            else {
                String error ="학번또는 비밀번호를 확인하세요";
                model.addAttribute("error",error);
                return "join,login/login";

            }

        }



    }

    @GetMapping("/student/login/main/{stunum}")
    public String stumain(@PathVariable Long stunum, Model model, HttpSession session){

        student student = (student) session.getAttribute("student");

        model.addAttribute("student", student);


        return "student/stuMain";
    }




}
