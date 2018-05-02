package campus.hackday.service;

import campus.hackday.dto.Comment;
import campus.hackday.dto.NgtReact;
import campus.hackday.dto.PstReact;
import campus.hackday.mapper.NgtReactMapper;
import campus.hackday.mapper.PstReactMapper;
import campus.hackday.model.ReactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("Duplicates")
@Service
public class PstReactService {

  @Autowired
  private CommentService commentService;
  @Autowired
  private PstReactMapper pstReactMapper;
  @Autowired
  private NgtReactMapper ngtReactMapper;

  // Todo 공감 || 비공감 중 1가지만 가능해야 한다.
  public void pstReact(int postId, int commentId, int userId) throws IllegalAccessException {
    Comment comment = commentService.findById(commentId);
    PstReact pstReact = findByCommentIdAndUserId(commentId, userId);
    NgtReact ngtReact = ngtReactMapper.findByCommentIdAndUserId(commentId, userId);

    if (ngtReact != null) {
      throw new IllegalAccessException("이 댓글에 비공감하셨습니다.");
    }
    // 해당 댓글에 공감 || 비공감 한 적 없으면
    else if (pstReact == null && ngtReact == null) {
      commentService.addPstReactCount(comment);
      insert(commentId, userId, postId);
    }
    // 해당 댓글에 공감한 적이 있으면
    else if (pstReact != null) {
      commentService.subPstReactCount(comment);
      delete(pstReact.getId());
    }
  }

  public PstReact findByCommentIdAndUserId(int commentId, int userId) {
    return pstReactMapper.findByCommentIdAndUserId(commentId, userId);
  }

  public void insert(int commentId, int userId, int postId) {
    ReactModel reactModel = new ReactModel();
    reactModel.setCommentId(commentId);
    reactModel.setUserId(userId);
    reactModel.setPostId(postId);
    pstReactMapper.insert(reactModel);
  }

  public void delete(int id) {
    pstReactMapper.delete(id);
  }

}
