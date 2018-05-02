package campus.hackday.controller;

import campus.hackday.dto.Comment;
import campus.hackday.model.DefaultResponse;
import campus.hackday.model.StatusEnum;
import campus.hackday.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("post")
public class CommentController {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private CommentService commentService;

  @GetMapping("{postId}/comments")
  public ResponseEntity<DefaultResponse> findAllCommentList(@PathVariable int postId) {
    DefaultResponse res = new DefaultResponse();
    List<Comment> comments = commentService.findAllByPostId(postId);
    res.setData(comments);
    res.setMsg(postId + "번 게시글의 댓글 목록");
    res.setStatusEnum(StatusEnum.SUCCESS);

    logger.info("postId: {}", postId);

    return new ResponseEntity<>(res, HttpStatus.OK);
  }


}
