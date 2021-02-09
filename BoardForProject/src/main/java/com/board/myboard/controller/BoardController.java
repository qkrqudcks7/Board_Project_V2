package com.board.myboard.controller;

import com.board.myboard.domain.Account;
import com.board.myboard.domain.Comments;
import com.board.myboard.domain.Content;
import com.board.myboard.form.BoardForm;
import com.board.myboard.form.CommentsForm;
import com.board.myboard.repository.ContentRepository;
import com.board.myboard.service.ContentService;
import com.board.myboard.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ContentRepository contentRepository;
    private final ContentService contentService;
    private final S3Service s3Service;

    @GetMapping("/board/main")
    public String main1(Model model,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Content> contentList = contentRepository.findAll(pageable);
        model.addAttribute("contentList",contentList);
        model.addAttribute("maxPage",10);
        model.addAttribute("check",0);
        return "board/main";
    }


    @GetMapping("/board/write")
    public String write(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        return "board/write";
    }

    @PostMapping("/board/write")
    public String writeUpdate(@CurrentAccount Account account,
                              @Valid BoardForm boardForm , Errors errors,
                              @RequestParam("img") MultipartFile files) throws IOException {
        if (errors.hasErrors()){
            return "board/write";
        }

        if (!files.isEmpty()){
            String fileName = s3Service.upload(files);
            boardForm.setImgUrl(fileName);
        }
        else {
            boardForm.setImgUrl("");
        }

        contentService.write(account,boardForm);
        return "redirect:/board/main";
    }

    @GetMapping("/board/read")
    public String read(@RequestParam("id") Long id,Model model,
                         @CurrentAccount Account account) {

        Content content = contentRepository.findById(id).get();
        contentService.countView(content);
        model.addAttribute("content",content);
        model.addAttribute("id",id);
        model.addAttribute("account",account);
        List<Comments> comments = content.getComments();
        model.addAttribute("comments",comments);
        model.addAttribute("commentForm",new CommentsForm());

        return "board/read";
    }

    @GetMapping("/board/modify")
    public String modify(@RequestParam("id") Long id,Model model) {
        Content content = contentRepository.findById(id).get();

        BoardForm boardForm = new BoardForm();
        boardForm.setSubject(content.getSubject());
        boardForm.setNickname(content.getAccount().getNickname());
        boardForm.setText(content.getText());
        boardForm.setImgUrl(content.getUrl());
        boardForm.setImgUrl(content.getUrl());

        model.addAttribute("content",content);
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("id",id);

        return "board/modify";
    }

    @PostMapping("/board/modify")
    public String modifyUpdate(@RequestParam("id") Long id,
                               @ModelAttribute Content content,
                               @Valid BoardForm boardForm, Errors errors,
                               @RequestParam("file") MultipartFile files) throws IOException {
        if (errors.hasErrors()){
            return "board/modify";
        }
        if (!files.isEmpty()){
            String fileName = s3Service.upload(files);
            boardForm.setImgUrl(fileName);
        }
        else {
            boardForm.setImgUrl("");
        }

        contentService.updateBoard(id,boardForm);

        return "redirect:/board/main";
    }

    @GetMapping("/board/delete")
    public String delete(@RequestParam("id") Long id){
        contentRepository.deleteById(id);

        return "board/delete";
    }

}
