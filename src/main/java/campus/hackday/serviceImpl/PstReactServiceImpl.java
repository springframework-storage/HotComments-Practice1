package campus.hackday.serviceImpl;

import campus.hackday.dto.Comment;
import campus.hackday.dto.NgtReact;
import campus.hackday.dto.PstReact;
import campus.hackday.mapper.PstReactMapper;
import campus.hackday.model.ReactModel;
import campus.hackday.service.PstReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("Duplicates")
@Service
public class PstReactServiceImpl implements PstReactService {

  @Autowired
  private CommentServiceImpl commentServiceImpl;
  @Autowired
  private PstReactMapper pstReactMapper;
  @Autowired
  private NgtReactServiceImpl ngtReactServiceImpl;

  @Override
  public void pstReact(int postId, int commentId, int userId) throws IllegalAccessException {
    Comment comment = commentServiceImpl.findById(commentId);
    PstReact pstReact = findByCommentIdAndUserId(commentId, userId);
    NgtReact ngtReact = ngtReactServiceImpl.findByCommentIdAndUserId(commentId, userId);

    if (ngtReact != null) {
      throw new IllegalAccessException("이 댓글에 비공감하셨습니다.");
    }
    // 해당 댓글에 공감 || 비공감 한 적 없으면
    else if (pstReact == null && ngtReact == null) {
      commentServiceImpl.addPstReactCount(comment);
      insert(commentId, userId, postId);
    }
    // 해당 댓글에 공감한 상태이면 있으면
    else if (pstReact != null) {
      commentServiceImpl.subPstReactCount(comment);
      delete(pstReact.getCommentId(), pstReact.getUserId());
    }
  }

  @Override
  public PstReact findByCommentIdAndUserId(int commentId, int userId) {
    return pstReactMapper.findByCommentIdAndUserId(commentId, userId);
  }

  @Override
  public void insert(int commentId, int userId, int postId) {
    ReactModel reactModel = new ReactModel();
    reactModel.setCommentId(commentId);
    reactModel.setUserId(userId);
    reactModel.setPostId(postId);
    pstReactMapper.insert(reactModel);
  }

  @Override
  public void delete(int commentId, int userId) {
    pstReactMapper.delete(commentId, userId);
  }

  @Override
  public void deleteAll() {
    pstReactMapper.deleteAll();
  }

}
