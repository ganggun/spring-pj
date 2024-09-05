package org.example.pj.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.pj.dto.DeleteDTO;
import org.example.pj.dto.SearchDTO;
import org.example.pj.dto.UpdatePostDTO;
import org.example.pj.entity.PostEntity;
import org.example.pj.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
public class MaimController {
    private final AuthenticationManager authenticationManager;

    public MaimController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private PostService postService;

    @Operation(summary = "User Login", description = "Authenticate user with username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
    @GetMapping("/")
    public String mainP() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "main controller" +" "+ name +" "+ role;
    }
    @PostMapping("/write")
    public String writeP(@RequestBody PostEntity postEntity) {
        postService.savePost(postEntity);
        return "redirect:/";

    }
    @PostMapping("/delete")
    public String deleteP(@RequestBody  DeleteDTO deleteDTO) {

        postService.deletePostById(deleteDTO);
        return "redirect:/";
//        처음부터 다시 dto에 유저네임 업세고 토큰에서 불러오기
    }
    @PostMapping("/edit")
    public String editP(@RequestBody UpdatePostDTO updatePostDTO){
        postService.updatePost(updatePostDTO);
        return "redirect:/";
    }
    @GetMapping("/search")
    public List<PostEntity> searchP(@RequestBody SearchDTO tag){
        log.info("searchP tag {}", tag.getTag());
        return postService.postSearch(tag.getTag());

    }
    @GetMapping("/main")
    public List<PostEntity> mainListP(){
        return postService.main();
    }
}
