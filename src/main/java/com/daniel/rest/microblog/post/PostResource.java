package com.daniel.rest.microblog.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
public class PostResource {

    @Autowired
    private PostJPARepository postJPARepository;

    // Read all the posts in the blog
    @RequestMapping(method = RequestMethod.GET, path = "/users/posts")
    public List<Post> getAllPosts() {
        return postJPARepository.findAll();
    }

    // Read all the posts of a specific user in the blog
    @RequestMapping(method = RequestMethod.GET, path = "/users/{username}/posts")
    public List<Post> getAllUserPosts(@PathVariable String username) {
        return postJPARepository.findByUsername(username);
    }

    // Read a specific post of a specific user in the blog
    @RequestMapping(method = RequestMethod.GET, path = "/users/{username}/posts/{id}")
    public Post getPost(@PathVariable String username, @PathVariable long id) {
        return postJPARepository.findById(id).get();
    }

    // Update a post of a specific user
    @RequestMapping(method = RequestMethod.PUT, path = "/users/{username}/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String username, @PathVariable long id, @RequestBody Post post) {
        if (post.getUsername() == null) {
            post.setUsername(username);
        }
        if (post.getId() == null || id != post.getId()) {
            post.setId(id);
        }

        if (post.getNumOfLikes() == null) {
            post.setNumOfLikes(0);
        }
        if (post.getCreatedTime() == null) {
            post.setCreatedTime(new Date());
        }

        Post updatedPost = this.postJPARepository.save(post);
        return new ResponseEntity<Post>(updatedPost, HttpStatus.OK);
    }

    // Create new post for specific user
    @RequestMapping(method = RequestMethod.POST, path = "/users/{username}/posts")
    public ResponseEntity<Void> createPost(@PathVariable String username, @RequestBody Post post) {
        post.setUsername(username);

        if (post.getNumOfLikes() == null) {
            post.setNumOfLikes(0);
        }
        if (post.getCreatedTime() == null) {
            post.setCreatedTime(new Date());
        }

        Post createdPost = this.postJPARepository.save(post);

        // Creating a new url with the new id assigning to that created new post
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(createdPost.getId()).
                toUri();

        return ResponseEntity.created(uri).build();
    }

    // Delete a user's post
    @RequestMapping(method = RequestMethod.DELETE, path = "/users/{username}/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String username, @PathVariable long id) {
        postJPARepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Read all the five-top trending posts with the most likes which published within the last 30 days
    @RequestMapping(method = RequestMethod.GET, path = "/users/posts/trendingPosts")
    public List<Post> getLastMonthPosts() {
        Date startDate = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_MONTH, -30);
        Date endDate = cal.getTime();

        return postJPARepository.findByCreatedTimeBetween(endDate, startDate,
                PageRequest.of(0, 5, Sort.Direction.DESC, "numOfLikes"));
    }
}
