package com.board.myboard.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "comment_id")
@Builder
public class Comments {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id",nullable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    public Comments(Content content, Account account, String comment, LocalDateTime localDateTime) {
        this.content = content;
        this.account = account;
        this.comment = comment;
        this.localDateTime = localDateTime;
    }
}
