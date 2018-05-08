package campus.hackday.redisService;

import org.springframework.stereotype.Service;

@Service
public interface ReactRedisToMySqlService {

  void updateReactCount(int postId);
  void updatePstReact(int postId);
  void updateNgtReact(int postId);

}
