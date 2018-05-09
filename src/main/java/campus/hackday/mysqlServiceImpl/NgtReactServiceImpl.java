package campus.hackday.mysqlServiceImpl;

import campus.hackday.dto.Comment;
import campus.hackday.dto.NgtReact;
import campus.hackday.dto.PstReact;
import campus.hackday.mapper.NgtReactMapper;
import campus.hackday.model.ReactModel;
import campus.hackday.service.NgtReactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("Duplicates")
@Service
public class NgtReactServiceImpl implements NgtReactService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private CommentServiceImpl commentServiceImpl;

  @Autowired
  private NgtReactMapper ngtReactMapper;

  @Autowired
  private PstReactServiceImpl pstReactServiceImpl;

  @Override
  public void ngtReact(int postId, int commentId, int userId) {
    Comment comment = commentServiceImpl.findById(commentId);
    PstReact pstReact = pstReactServiceImpl.findByCommentIdAndUserId(commentId, userId);
    NgtReact ngtReact = this.findByCommentIdAndUserId(commentId, userId);

    if (pstReact != null) {
      logger.error("이미 이 댓글에 공감하셨습니다.");
    }
    // 해당 댓글에 공감 || 비공감 한 적 없으면
    if (pstReact == null && ngtReact == null) {
      commentServiceImpl.addNgtReactCount(comment);
      this.insert(commentId, userId, postId);
    }
    // 해당 댓글에 비공감한 상태이면 있으면
    else if (ngtReact != null) {
      commentServiceImpl.subNgtReactCount(comment);
      this.delete(ngtReact.getCommentId(), ngtReact.getUserId());
    }

  }

  @Override
  public void insert(int commentId, int userId, int postId) {
    ReactModel reactModel = new ReactModel();
    reactModel.setCommentId(commentId);
    reactModel.setUserId(userId);
    reactModel.setPostId(postId);
    ngtReactMapper.insert(reactModel);
  }

  @Override
  public void delete(int commentId, int userId) {
    ngtReactMapper.delete(commentId, userId);
  }

  public NgtReact findByCommentIdAndUserId(int commentId, int userId) {
    return ngtReactMapper.findByCommentIdAndUserId(commentId, userId);
  }

  public void deleteAll() {
    ngtReactMapper.deleteAll();
  }

}
