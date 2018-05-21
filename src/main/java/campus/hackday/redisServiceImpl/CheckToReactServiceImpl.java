package campus.hackday.redisServiceImpl;

import campus.hackday.model.ReactParam;
import campus.hackday.service.CheckToReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckToReactServiceImpl implements CheckToReactService {

  @Autowired
  private RedisPstReactServiceImpl redisPstReactServiceImpl;

  @Autowired
  private RedisNgtReactServiceImpl redisNgtReactServiceImpl;

  @Override
  public void checkToReact(int postId, int commentId, int userId, ReactParam reactParam) {

    if (ReactParam.Pst.equals("Pst")) {
      redisPstReactServiceImpl.pstReact(postId, commentId, userId);
    }
    else if (ReactParam.Ngt.equals("Ngt")) {
      redisNgtReactServiceImpl.ngtReact(postId, commentId, userId);
    }

  }

}
