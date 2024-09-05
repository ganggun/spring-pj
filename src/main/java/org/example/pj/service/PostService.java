package org.example.pj.service;



import lombok.extern.slf4j.Slf4j;
import org.example.pj.Repository.PostRepository;
import org.example.pj.dto.DeleteDTO;

import org.example.pj.dto.UpdatePostDTO;
import org.example.pj.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    public void savePost (PostEntity postEntity){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        postEntity.setUsername(username);

        postRepository.save(postEntity);
    }

    public void deletePostById (DeleteDTO deleteDTO){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer id = deleteDTO.getPostId();
        Optional<PostEntity> postOptional = postRepository.findById(id);
        if (postOptional.isPresent() ) {
            System.out.println(name);
            PostEntity postEntity = postOptional.get();
            // 게시글 작성자와 현재 사용자의 이름 비교
            if (postEntity.getUsername().equals(name)) {
                postRepository.deleteById(id);
                log.info( name);
            } else {
                return;
                //여기 이름 안같을때 얘외
            }
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
    public void updatePost (UpdatePostDTO updatePostDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<PostEntity> postOptional = postRepository.findById(updatePostDTO.getPostId());
        int postId = updatePostDTO.getPostId();
        log.info("Attempting to update post with ID: {}", postId);

        if (postOptional.isPresent()) {
            PostEntity postEntity = postOptional.get();

            if (postEntity.getUsername().equals(username)) {

                postEntity.setTitle(updatePostDTO.getTitle());
                postEntity.setText(updatePostDTO.getText());
                postEntity.setTag(updatePostDTO.getTag());
                postEntity.setDate(updatePostDTO.getDate());

                postRepository.save(postEntity);

            } else {
                throw new RuntimeException("게시글을 지울 권한이 없습니다");
            }
        } else {
            throw new RuntimeException("게시글을 찾지 못하였습니다");
        }
    }
    public List<PostEntity> postSearch (String tag){
        return postRepository.findAllByTag(tag);
    }
    public List<PostEntity> main(){
        return postRepository.findAllPostsSortedByDate();
    }
}