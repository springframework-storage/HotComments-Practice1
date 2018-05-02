package campus.hackday.service;

import campus.hackday.dto.Comment;
import campus.hackday.dto.NgtReact;
import campus.hackday.dto.PstReact;
import campus.hackday.mapper.NgtReactMapper;
import campus.hackday.model.ReactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("Duplicates")
@Service
public class NgtReactService {

  @Autowired
  private CommentService commentService;
  @Autowired
  private NgtReactMapper ngtReactMapper;
  @Autowired
  private PstReactService pstReactService;

  // Todo 공감 || 비공감 중 1가지만 가능해야 한다.
  public void ngtReact(int postId, int commentId, int userId) throws IllegalAccessException {
    Comment comment = commentService.findById(commentId);
    PstReact pstReact = pstReactService.findByCommentIdAndUserId(commentId, userId);
    NgtReact ngtReact = findByCommentIdAndUserId(commentId, userId);

    if (pstReact != null) {
      throw new IllegalAccessException("이 댓글에 공감하셨습니다.");
    }
    // 해당 댓글에 공감 || 비공감 한 적 없으면
    if (pstReact == null && ngtReact == null) {
      commentService.addNgtReactCount(comment);
      insert(commentId, userId, postId);
    }
    // 해당 댓글에 비공감한 적이 있으면
    else if (ngtReact != null) {
      commentService.subNgtReactCount(comment);
      delete(ngtReact.getId());
    }

  }

  public NgtReact findByCommentIdAndUserId(int commentId, int userId) {
    return ngtReactMapper.findByCommentIdAndUserId(commentId, userId);
  }

  public void insert(int commentId, int userId, int postId) {
    ReactModel reactModel = new ReactModel();
    reactModel.setCommentId(commentId);
    reactModel.setUserId(userId);
    reactModel.setPostId(postId);
    ngtReactMapper.insert(reactModel);
  }

  public void delete(int id) {
    ngtReactMapper.delete(id);
  }

}
