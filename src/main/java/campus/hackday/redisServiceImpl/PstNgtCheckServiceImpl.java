package campus.hackday.redisServiceImpl;

import campus.hackday.dto.Comment;
import campus.hackday.redisService.PstNgtCheckService;
import campus.hackday.serviceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PstNgtCheckServiceImpl implements PstNgtCheckService {

  @Autowired
  private RedisPstReactServiceImpl redisPstReactServiceImpl;
  @Autowired
  private RedisNgtReactServiceImpl redisNgtReactServiceImpl;
  @Autowired
  private CommentServiceImpl commentServiceImpl;

  @Override
  public void check(int commentId, int userId, String react) {

    Comment comment = commentServiceImpl.findById(commentId);

    if (react.equals("pst")) {
      redisPstReactServiceImpl.insert(comment.getId(), userId);
    }
    else if (react.equals("ngt")) {
      redisNgtReactServiceImpl.insert(comment.getId(), userId);
    }

  }

}
