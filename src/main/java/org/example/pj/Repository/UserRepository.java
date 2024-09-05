package org.example.pj.Repository;

import org.example.pj.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);
//    유저 이름 확인
    UserEntity findByUsername(String username);
}
