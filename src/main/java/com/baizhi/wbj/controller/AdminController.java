package com.baizhi.wbj.controller;

import com.baizhi.wbj.entity.Admin;
import com.baizhi.wbj.service.AdminService;

import com.baizhi.wbj.config.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/createImg")
    public void creatImg(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode createValidateCode = new CreateValidateCode();
        String code = createValidateCode.getCode();
        session.setAttribute("code",code);
        createValidateCode.write(response.getOutputStream());
    }

    @GetMapping("/login")
    @ResponseBody
    public Map login(String yzm, Admin admin){
        Map map = adminService.login(yzm, admin.getUsername(), admin.getPassword());
        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        Object admin = session.getAttribute("currentAdmin");
        if(admin!=null){
            session.removeAttribute("currentAdmin");
            return "redirect:/jsp/login.jsp";
        }else{
            return "redirect:/jsp/login.jsp";
        }
    }
}
