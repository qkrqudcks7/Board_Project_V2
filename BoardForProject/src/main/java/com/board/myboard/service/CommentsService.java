package com.board.myboard.service;

import com.board.myboard.domain.Comments;
import com.board.myboard.domain.Content;
import com.board.myboard.form.CommentsForm;
import com.board.myboard.repository.CommentsRepository;
import com.board.myboard.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final ContentRepository contentRepository;
    private final ContentService contentService;

    public void addComment(Long id, CommentsForm commentsForm){
        Content content = contentRepository.findById(id).get();
        Comments comments = new Comments(content,commentsForm.getAccount(),commentsForm.getComment(), LocalDateTime.now());
        commentsRepository.save(comments);
    }
}
