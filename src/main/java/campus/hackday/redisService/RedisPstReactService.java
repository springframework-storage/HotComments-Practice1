package campus.hackday.redisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class RedisPstReactService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private static final String KEY = "Pst";
  private RedisTemplate<String, Integer> redisTemplate;
  private SetOperations<String, Integer> setOperations;

  @Autowired
  private RedisNgtReactService redisNgtReactService;

  @Autowired
  public RedisPstReactService(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  private void init() {
    setOperations = redisTemplate.opsForSet();
  }

  // 해당 키(댓글ID)의 공감 수
  public int countMemberByKey(int commentId) {
    return setOperations.members(KEY + Integer.toString(commentId)).size();
    // return setOperations.size(KEY + Integer.toString(commentId));
  }

  // 공감 요청
  public void insert(int commentId, int userId) {
    // 이미 해당 댓글에 비공감 했을 경우
    if (redisNgtReactService.isMember(commentId, userId)) {
      logger.error("이미 이 댓글에 비공감하셨습니다.");
    }
    // 이미 해당 댓글에 공감 했던 경우 공감 삭제
    else if (this.isMember(commentId, userId)) {
      setOperations.remove(KEY + Integer.toString(commentId), userId);
    }
    // 둘 다 아니라면 공감 삽입
    else {
      setOperations.add(KEY + Integer.toString(commentId), userId);
    }
  }

  public Set<Integer> members(int commentId) {
    return setOperations.members(KEY + Integer.toString(commentId));
  }

  // 잘 들어갔는지 확인용
  public boolean isMember(int commentId, int userId) {
    return setOperations.isMember(KEY + Integer.toString(commentId), userId);
  }

}
