package campus.hackday.controller;

import campus.hackday.dto.Comment;
import campus.hackday.model.DefaultResponse;
import campus.hackday.model.StatusEnum;
import campus.hackday.redisServiceImpl.ReactRedisToMySqlServiceImpl;
import campus.hackday.serviceImpl.CommentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableCaching
@RestController
@RequestMapping("post")
public class CommentController {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private CommentServiceImpl commentServiceImpl;
  @Autowired
  private ReactRedisToMySqlServiceImpl reactRedisToMySqlServiceImpl;

  @GetMapping("{postId}/comments")
  public ResponseEntity<DefaultResponse> findAllCommentList(@PathVariable int postId) {
    long start = System.currentTimeMillis();    // 시작 시간

    DefaultResponse res = new DefaultResponse();
    List<Comment> comments = commentServiceImpl.findAllByPostIdOrderByTotalDesc(postId);

    long end = System.currentTimeMillis();      // 끝 시간
    logger.info("EHCache의 수행 시간: {}", Long.toString(end - start));  // 시간 측정

    reactRedisToMySqlServiceImpl.updateReactCount(postId);
    reactRedisToMySqlServiceImpl.updatePstReact(postId);
    reactRedisToMySqlServiceImpl.updateNgtReact(postId);

    res.setData(comments);
    res.setMsg(postId + "번 게시글의 댓글 목록");
    res.setStatusEnum(StatusEnum.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }


}
