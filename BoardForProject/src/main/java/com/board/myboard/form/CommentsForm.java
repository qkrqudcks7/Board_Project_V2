package com.board.myboard.form;

import com.board.myboard.domain.Account;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentsForm {

    private Account account;

    @NotBlank
    private String comment;
}
