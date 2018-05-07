package campus.hackday.controller;

import campus.hackday.dto.Comment;
import campus.hackday.model.DefaultResponse;
import campus.hackday.service.CommentService;
import campus.hackday.redisService.RedisNgtReactService;
import campus.hackday.redisService.RedisPstReactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisReactController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private RedisPstReactService redisPstReactService;
  @Autowired
  private RedisNgtReactService redisNgtReactService;
  @Autowired
  private CommentService commentService;

  @GetMapping("{postId}/{commentId}/{userId}/pst")
  public ResponseEntity<DefaultResponse> pstReact
          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) {

    DefaultResponse res = new DefaultResponse();
    Comment comment = commentService.findById(commentId);

    redisPstReactService.insert(comment.getId(), userId);

//    res.setData();
//    res.setMsg();
//    res.setStatusEnum(StatusEnum.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("{postId}/{commentId}/{userId}/ngt")
  public ResponseEntity<DefaultResponse> ngtReact
          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) {

    DefaultResponse res = new DefaultResponse();
    Comment comment = commentService.findById(commentId);

    redisNgtReactService.insert(comment.getId(), userId);

//    res.setData();
//    res.setMsg();
//    res.setStatusEnum(StatusEnum.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

}
