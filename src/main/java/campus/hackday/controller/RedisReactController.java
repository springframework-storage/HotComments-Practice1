package campus.hackday.controller;

import campus.hackday.model.DefaultResponse;
import campus.hackday.redisServiceImpl.PstNgtCheckServiceImpl;
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
  private PstNgtCheckServiceImpl checkService;


  // 공감/비공감 API 통합
  @GetMapping("{commentId}/{userId}/{react}")
  public ResponseEntity<DefaultResponse> IntegrationReact
          (@PathVariable int commentId, @PathVariable int userId, @PathVariable String react) {

    DefaultResponse res = new DefaultResponse();
    checkService.check(commentId, userId, react);

//    res.setData();
//    res.setMsg();
//    res.setStatusEnum(StatusEnum.SUCCESS);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }



//  @GetMapping("{postId}/{commentId}/{userId}/pst")
//  public ResponseEntity<DefaultResponse> pstReact
//          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) {
//
//    DefaultResponse res = new DefaultResponse();
//    Comment comment = commentService.findById(commentId);
//
//    redisPstReactService.insert(comment.getId(), userId);
//
////    res.setData();
////    res.setMsg();
////    res.setStatusEnum(StatusEnum.SUCCESS);
//    return new ResponseEntity<>(res, HttpStatus.OK);
//  }
//
//  @GetMapping("{postId}/{commentId}/{userId}/ngt")
//  public ResponseEntity<DefaultResponse> ngtReact
//          (@PathVariable int postId, @PathVariable int commentId, @PathVariable int userId) {
//
//    DefaultResponse res = new DefaultResponse();
//    Comment comment = commentService.findById(commentId);
//
//    redisNgtReactService.insert(comment.getId(), userId);
//
////    res.setData();
////    res.setMsg();
////    res.setStatusEnum(StatusEnum.SUCCESS);
//    return new ResponseEntity<>(res, HttpStatus.OK);
//  }

}
