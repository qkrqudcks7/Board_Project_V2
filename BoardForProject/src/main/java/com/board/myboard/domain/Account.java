package com.board.myboard.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "account_id")
@Builder
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String bio;

    @Column(nullable = true)
    private String job;

    @OneToMany(mappedBy = "account")
    private List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
    private List<Comments> comments;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Follow> followings;

    @OneToMany(mappedBy = "follower" , cascade = CascadeType.ALL)
    private List<Follow> followers;


}
