package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.exception.member.NotFoundMemberException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects")
public class ProjectRestController {
    private final ProjectMemberService projectMemberService;

    @GetMapping
    public List<Project> getProjects(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotFoundMemberException("로그인이 필요합니다.");
        }
        Object loginMember = session.getAttribute("loginMember");
        String id = loginMember.getMemberId();


        return projectMemberService.getProjects();
    }
}
