package campus.hackday.controller;

import campus.hackday.dto.Comment;
import campus.hackday.model.DefaultResponse;
import campus.hackday.model.StatusEnum;
import campus.hackday.service.CommentService;
import campus.hackday.service.NgtReactService;
import campus.hackday.service.PstReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class ReactController {

  @Autowired
  private CommentService commentService;
  @Autowired
  private PstReactService pstReactService;
  @Autowired
  private NgtReactService ngtReactService;

  // 공감 요청
  @GetMapping("{postId}/{commentId}/{userId}/pst")
  public ResponseEntity<DefaultResponse> pstReact
          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) throws IllegalAccessException {

    DefaultResponse res = new DefaultResponse();
    Comment comment = commentService.findById(commentId);
    pstReactService.pstReact(postId, comment.getId(), userId);

    res.setData(comment);
    res.setMsg("공감 요청 결과 comment");
    res.setStatusEnum(StatusEnum.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  // 비공감 요청
  @GetMapping("{postId}/{commentId}/{userId}/ngt")
  public ResponseEntity<DefaultResponse> ngtReact
          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) throws IllegalAccessException {

    DefaultResponse res = new DefaultResponse();
    Comment comment = commentService.findById(commentId);
    ngtReactService.ngtReact(postId, comment.getId(), userId);

    res.setData(comment);
    res.setMsg("비공감 요청 결과 comment");
    res.setStatusEnum(StatusEnum.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
