package com.rest.api.jpa.springboot.user;


import com.rest.api.jpa.springboot.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> findUserById(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("id= "+id);
        }
       /* Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;*/

        EntityModel<User> resource = new EntityModel<User>(user.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;


    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }



    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){

        userRepository.deleteById(id);

    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> findAllUsersPosts(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("id = "+id);
        }
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Post post){

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            throw new UserNotFoundException("id = "+id);
        }
        User user = userOptional.get();

        post.setUser(user);
        postRepository.save(post);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}

