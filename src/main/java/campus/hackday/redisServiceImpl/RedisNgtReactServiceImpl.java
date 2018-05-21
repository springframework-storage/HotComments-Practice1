package campus.hackday.redisServiceImpl;

import campus.hackday.service.NgtReactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class RedisNgtReactServiceImpl implements NgtReactService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private static final String KEY = "Ngt";
  private RedisTemplate<String, Integer> redisTemplate;
  private SetOperations<String, Integer> setOperations;

  @Autowired
  private RedisPstReactServiceImpl redisPstReactServiceImpl;

  @Autowired
  public RedisNgtReactServiceImpl(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  private void init() {
    setOperations = redisTemplate.opsForSet();
  }

  // 비공감 요청
  @Override
  public void ngtReact(int postId, int commentId, int userId) {
    // 이미 해당 댓글에 공감했을 경우
    if (redisPstReactServiceImpl.isMember(commentId, userId)) {
      logger.error("이미 이 댓글에 공감하셨습니다.");
    }
    // 이미 해당 댓글에 비공감 했던 경우 비공감 삭제
    else if (this.isMember(commentId, userId)) {
      this.delete(commentId, userId);
    }
    // 둘 다 아니라면 비공감 삽입
    else {
      this.insert(commentId, userId, postId);
    }
  }

  // 잘 들어갔는지 확인용
  public boolean isMember(int commentId, int userId) {
    return this.setOperations.isMember(KEY + Integer.toString(commentId), userId);
  }

  private void insert(int commentId, int userId, int postId) {
    this.setOperations.add(KEY + Integer.toString(commentId), userId);
  }

  private void delete(int commentId, int userId) {
    this.setOperations.remove(KEY + Integer.toString(commentId), userId);
  }

  private Set<Integer> members(int commentId) {
    return this.setOperations.members(KEY + Integer.toString(commentId));
  }

  // 해당 키(댓글ID)의 비공감 수
  private int countMemberByKey(int commentId) {
    return this.setOperations.members(KEY + Integer.toString(commentId)).size();
    // return setOperations.size(KEY + Integer.toString(commentId));
  }

}
