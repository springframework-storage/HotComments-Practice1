package campus.hackday.redisService;

import campus.hackday.dto.Comment;
import campus.hackday.service.CommentService;
import campus.hackday.service.NgtReactService;
import campus.hackday.service.PstReactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("Duplicates")
@Service
public class ReactCountRedisToMySqlService {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private RedisPstReactService redisPstReactService;
  @Autowired
  private RedisNgtReactService redisNgtReactService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private PstReactService pstReactService;
  @Autowired
  private NgtReactService ngtReactService;

  // 공감/비공감 개수 갱신
  public void updateReactCount(int postId) {
    List<Comment> comments = commentService.findAllByPostId(postId);

    for (Comment c : comments) {
      // Redis에서 commentId 별 pCount, nCount를 뽑아낸다.
      int pCount = redisPstReactService.countMemberByKey(c.getId());
      int nCount = redisNgtReactService.countMemberByKey(c.getId());

      // Redis에서 뽑아낸 commentId별 pCount, nCount, total을 MySQL에 저장
      commentService.updateReactCount(c.getId(), pCount, nCount);
    }
  }

  // MySQL(PstReact)에 해당 Redis 데이터 저장
  public void updatePstReact(int postId) {
    List<Comment> comments = commentService.findAllByPostId(postId);
    Object[] pstArr = null;
    /*
    Redis에 데이터가 없으면 MySQL에서도 지워야하는데
    이미 Redis에서는 지워지고 없는 데이터라 여기서는 비교할 수 없어서
    일단은 전부 지우고 매번 다시 갱신하는 것으로 구현
     */
    pstReactService.deleteAll();

    for (Comment c : comments) {
      pstArr = redisPstReactService.members(c.getId()).toArray();

      for (int i = 0; i < pstArr.length; ++i) {

        int userId = Integer.parseInt(pstArr[i].toString());
        // Redis에 데이터가 있으면 MySQL에도 저장한다.
        if (redisPstReactService.isMember(c.getId(), userId) == true) {
          pstReactService.insert(c.getId(), userId, postId);
        }

      }

    }

  }

  // MySQL(NgtReact)에 해당 Redis 데이터 저장
  public void updateNgtReact(int postId) {
    List<Comment> comments = commentService.findAllByPostId(postId);
    Object[] ngtArr = null;
    /*
    Redis에 데이터가 없으면 MySQL에서도 지워야하는데
    이미 Redis에서는 지워지고 없는 데이터라 여기서는 비교할 수 없어서
    일단은 전부 지우고 매번 다시 갱신하는 것으로 구현
     */
    ngtReactService.deleteAll();

    for (Comment c : comments) {
      ngtArr = redisNgtReactService.members(c.getId()).toArray();

      for(int i = 0; i < ngtArr.length; ++i) {

        int userId = Integer.parseInt(ngtArr[i].toString());
        // Redis에 데이터가 있으면 MySQL에도 저장한다.
        if (redisNgtReactService.isMember(c.getId(), userId) == true) {
          ngtReactService.insert(c.getId(), userId, postId);
        }

      }

    }

  }

}
