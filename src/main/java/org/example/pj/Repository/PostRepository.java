package org.example.pj.Repository;

import org.example.pj.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {

    Optional<PostEntity> findById(int id);

    List<PostEntity> findAllByTag(String tag);

    @Query("SELECT p FROM PostEntity p ORDER BY p.date DESC")
    List<PostEntity> findAllPostsSortedByDate();
}
