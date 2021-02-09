package com.board.myboard.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "content_id")
@Builder
public class Content {

    @Id @GeneratedValue
    @Column(name = "content_id")
    private Long id;

    private String subject;

    private String text;

    @Column(nullable = true)
    private String url;

    private String localDateTime;

    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "content" , cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    public void plusViewCount() {
        this.viewCount++;
    }
}
