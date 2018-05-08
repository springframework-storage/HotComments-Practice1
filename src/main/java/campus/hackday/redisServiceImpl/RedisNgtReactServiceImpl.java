package campus.hackday.redisServiceImpl;

import campus.hackday.redisService.RedisNgtReactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class RedisNgtReactServiceImpl implements RedisNgtReactService {

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

  // 해당 키(댓글ID)의 비공감 수
  @Override
  public int countMemberByKey(int commentId) {
    return setOperations.members(KEY + Integer.toString(commentId)).size();
    // return setOperations.size(KEY + Integer.toString(commentId));
  }

  // 비공감 요청
  @Override
  public void insert(int commentId, int userId) {
    // 이미 해당 댓글에 공감했을 경우
    if (redisPstReactServiceImpl.isMember(commentId, userId)) {
      logger.error("이미 이 댓글에 공감하셨습니다.");
    }
    // 이미 해당 댓글에 비공감 했던 경우 비공감 삭제
    else if (this.isMember(commentId, userId)) {
      setOperations.remove(KEY + Integer.toString(commentId), userId);
    }
    // 둘 다 아니라면 비공감 삽입
    else {
      setOperations.add(KEY + Integer.toString(commentId), userId);
    }
  }

  @Override
  public Set<Integer> members(int commentId) {
    return setOperations.members(KEY + Integer.toString(commentId));
  }

  // 잘 들어갔는지 확인용
  @Override
  public boolean isMember(int commentId, int userId) {
    return setOperations.isMember(KEY + Integer.toString(commentId), userId);
  }

}
