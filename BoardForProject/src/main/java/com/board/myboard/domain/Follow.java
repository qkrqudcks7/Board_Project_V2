package com.board.myboard.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account following;

    @ManyToOne
    private Account follower;
}
