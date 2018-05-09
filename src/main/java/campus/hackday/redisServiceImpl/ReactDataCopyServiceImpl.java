package campus.hackday.redisServiceImpl;

import campus.hackday.dto.Comment;
import campus.hackday.service.ReactDataCopyService;
import campus.hackday.mysqlServiceImpl.CommentServiceImpl;
import campus.hackday.mysqlServiceImpl.NgtReactServiceImpl;
import campus.hackday.mysqlServiceImpl.PstReactServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("Duplicates")
@Service
public class ReactDataCopyServiceImpl implements ReactDataCopyService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private RedisPstReactServiceImpl redisPstReactServiceImpl;

  @Autowired
  private RedisNgtReactServiceImpl redisNgtReactServiceImpl;

  @Autowired
  private CommentServiceImpl commentServiceImpl;

  @Autowired
  private PstReactServiceImpl pstReactServiceImpl;

  @Autowired
  private NgtReactServiceImpl ngtReactServiceImpl;

  // 공감/비공감 개수 갱신
  @Override
  public void updateReactCount(int postId) {
    List<Comment> comments = commentServiceImpl.findAllByPostId(postId);

    for (Comment c : comments) {
      // Redis에서 commentId 별 pCount, nCount를 뽑아낸다.
      int pCount = redisPstReactServiceImpl.countMemberByKey(c.getId());
      int nCount = redisNgtReactServiceImpl.countMemberByKey(c.getId());

      // Redis에서 뽑아낸 commentId별 pCount, nCount, total을 MySQL에 저장
      commentServiceImpl.updateReactCount(c.getId(), pCount, nCount);
    }
  }

  // MySQL(PstReact)에 해당 Redis 데이터 저장
  @Override
  public void updatePstReact(int postId) {
    List<Comment> comments = commentServiceImpl.findAllByPostId(postId);
    Object[] pstArr = null;
    /*
    Redis에 데이터가 없으면 MySQL에서도 지워야하는데
    이미 Redis에서는 지워지고 없는 데이터라 여기서는 비교할 수 없어서
    일단은 전부 지우고 매번 다시 갱신하는 것으로 구현
     */
    pstReactServiceImpl.deleteAll();

    for (Comment c : comments) {
      pstArr = redisPstReactServiceImpl.members(c.getId()).toArray();

      for (int i = 0; i < pstArr.length; ++i) {

        int userId = Integer.parseInt(pstArr[i].toString());
        // Redis에 데이터가 있으면 MySQL에도 저장한다.
        if (redisPstReactServiceImpl.isMember(c.getId(), userId) == true) {
          pstReactServiceImpl.insert(c.getId(), userId, postId);
        }
      }
    }

  }

  // MySQL(NgtReact)에 해당 Redis 데이터 저장
  @Override
  public void updateNgtReact(int postId) {
    List<Comment> comments = commentServiceImpl.findAllByPostId(postId);
    Object[] ngtArr = null;
    /*
    Redis에 데이터가 없으면 MySQL에서도 지워야하는데
    이미 Redis에서는 지워지고 없는 데이터라 여기서는 비교할 수 없어서
    일단은 전부 지우고 매번 다시 갱신하는 것으로 구현
     */
    ngtReactServiceImpl.deleteAll();

    for (Comment c : comments) {
      ngtArr = redisNgtReactServiceImpl.members(c.getId()).toArray();

      for(int i = 0; i < ngtArr.length; ++i) {

        int userId = Integer.parseInt(ngtArr[i].toString());
        // Redis에 데이터가 있으면 MySQL에도 저장한다.
        if (redisNgtReactServiceImpl.isMember(c.getId(), userId) == true) {
          ngtReactServiceImpl.insert(c.getId(), userId, postId);
        }
      }
    }

  }

}
