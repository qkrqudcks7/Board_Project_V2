package com.board.myboard.controller;

import com.board.myboard.domain.Account;
import com.board.myboard.form.CommentsForm;
import com.board.myboard.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/comments/{id}")
    public String comments(@PathVariable("id") Long id, CommentsForm commentsForm,
                           @CurrentAccount Account account){
        if(account ==null) {
            return "redirect:/";
        }
        commentsForm.setAccount(account);
        commentsService.addComment(id,commentsForm);

        String url="redirect:/board/read?id="+id;
        return url;
    }
}
