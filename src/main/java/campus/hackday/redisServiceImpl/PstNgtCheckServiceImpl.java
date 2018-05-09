package campus.hackday.redisServiceImpl;

import campus.hackday.dto.Comment;
import campus.hackday.service.PstNgtCheckService;
import campus.hackday.mysqlServiceImpl.CommentServiceImpl;
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
  public void check(int postId, int commentId, int userId, String react) {

    Comment comment = commentServiceImpl.findById(commentId);

    if (react.equals("pst")) {
      redisPstReactServiceImpl.pstReact(postId, comment.getId(), userId);
    }
    else if (react.equals("ngt")) {
      redisNgtReactServiceImpl.ngtReact(postId, comment.getId(), userId);
    }

  }

}
