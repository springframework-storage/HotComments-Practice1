package campus.hackday.redisService;

import campus.hackday.dto.Comment;
import campus.hackday.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactCountRedisToMySqlService {

  @Autowired
  private RedisPstReactService redisPstReactService;
  @Autowired
  private RedisNgtReactService redisNgtReactService;
  @Autowired
  private CommentService commentService;

  // 공감/비공감 개수 갱신
  public void updateReactCount(int postId) {
    // 게시글 별(postId) 댓글 목록을 가져온다.
    List<Comment> comments = commentService.findAllByPostId(postId);

    for (Comment c : comments) {
      // Redis에서 commentId 별 pCount, nCount를 뽑아낸다.
      int pCount = redisPstReactService.countMemberByKey(c.getId());
      int nCount = redisNgtReactService.countMemberByKey(c.getId());

      // Redis에서 뽑아낸 commentId별 pCount, nCount, total을 MySQL에 저장
      commentService.updateReactCount(c.getId(), pCount, nCount);
    }
  }

}
