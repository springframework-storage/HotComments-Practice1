package campus.hackday.controller;

import campus.hackday.dto.Comment;
import campus.hackday.model.DefaultResponse;
import campus.hackday.model.Status;
import campus.hackday.mysqlServiceImpl.CommentServiceImpl;
import campus.hackday.mysqlServiceImpl.NgtReactServiceImpl;
import campus.hackday.mysqlServiceImpl.PstReactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("react")
public class ReactController {

  @Autowired
  private CommentServiceImpl commentServiceImpl;

  @Autowired
  private PstReactServiceImpl pstReactServiceImpl;

  @Autowired
  private NgtReactServiceImpl ngtReactServiceImpl;

  // 공감 요청
  @GetMapping("{postId}/{commentId}/{userId}/pst")
  public ResponseEntity<DefaultResponse> pstReact
          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) {

    DefaultResponse res = new DefaultResponse();
    Comment comment = commentServiceImpl.findById(commentId);
    pstReactServiceImpl.pstReact(postId, comment.getId(), userId);

    res.setData(comment);
    res.setMsg("공감 요청 결과 comment");
    res.setStatus(Status.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  // 비공감 요청
  @GetMapping("{postId}/{commentId}/{userId}/ngt")
  public ResponseEntity<DefaultResponse> ngtReact
          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) {

    DefaultResponse res = new DefaultResponse();
    Comment comment = commentServiceImpl.findById(commentId);
    ngtReactServiceImpl.ngtReact(postId, comment.getId(), userId);

    res.setData(comment);
    res.setMsg("비공감 요청 결과 comment");
    res.setStatus(Status.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
