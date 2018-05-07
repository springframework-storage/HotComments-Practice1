package campus.hackday.redisService;

import campus.hackday.dto.Comment;
import campus.hackday.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PstNgtCheckService {

  @Autowired
  private RedisPstReactService redisPstReactService;
  @Autowired
  private RedisNgtReactService redisNgtReactService;
  @Autowired
  private CommentService commentService;

  public void check(int commentId, int userId, String react) {
    Comment comment = commentService.findById(commentId);

    if (react.equals("pst")) {
      redisPstReactService.insert(comment.getId(), userId);
    }
    else if (react.equals("ngt")) {
      redisNgtReactService.insert(comment.getId(), userId);
    }
  }

}
