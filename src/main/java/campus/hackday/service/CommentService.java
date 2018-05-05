package campus.hackday.service;

import campus.hackday.dto.Comment;
import campus.hackday.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;

  @Cacheable(value = "ehcache")
  public List<Comment> findAllByPostId(int postId) {
    return commentMapper.findAllByPostId(postId);
  }

  public Comment findById(int id) {
    return commentMapper.findById(id);
  }

  void addPstReactCount(Comment comment) {
    commentMapper.addPstReactCount(comment);
  }

  void subPstReactCount(Comment comment) {
    commentMapper.subPstReactCount(comment);
  }

  void addNgtReactCount(Comment comment) {
    commentMapper.addNgtReactCount(comment);
  }

  void subNgtReactCount(Comment comment) {
    commentMapper.subNgtReactCount(comment);
  }

  // Redis -> MySQL (pCount, nCount, total) by commentId
  public void updateReactCount(int id, int pCount, int nCount) {
    Comment comment = findById(id);
    comment.setPCount(pCount);
    comment.setNCount(nCount);
    comment.setTotal(pCount - nCount);
    commentMapper.updateReactCount(comment);
  }

}
