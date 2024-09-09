package org.example.pj.controller;

import org.example.pj.dto.JoinDTO;
import org.example.pj.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {
    private final JoinService joinService;
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }
//    필드주입 생성자 주입
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(JoinDTO joinDTO) {
//        여기도 RequestBody 로 해야되는거 아님?
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("User add successfully");
    }
}
