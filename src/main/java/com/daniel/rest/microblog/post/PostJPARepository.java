package com.daniel.rest.microblog.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PostJPARepository extends JpaRepository<Post,Long> {

    List<Post> findByUsername(String username);
    List<Post> findByCreatedTimeBetween(Date endDate, Date startDate, Pageable topFiveDescLikes);
}
