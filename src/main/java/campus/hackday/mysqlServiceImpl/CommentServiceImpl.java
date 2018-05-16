package campus.hackday.mysqlServiceImpl;

import campus.hackday.dto.Comment;
import campus.hackday.mapper.CommentMapper;
import campus.hackday.service.CacheService;
import campus.hackday.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService, CacheService {

  @Autowired
  private CommentMapper commentMapper;

  @Override
  public List<Comment> findAll() {
    return commentMapper.findAll();
  }

//  @Cacheable(value = "ehcache")
  @Override
  public List<Comment> findAllByPostId(int postId) {
    return commentMapper.findAllByPostId(postId);
  }

  @Override
  @Cacheable(value = "ehcache")
  public List<Comment> findAllByPostIdOrderByTotalDesc(int postId) {
    return commentMapper.findAllByPostIdOrderByTotalDesc(postId);
  }

  @Override
  public Comment findById(int id) {
    return commentMapper.findById(id);
  }

  @Override
  public void addPstReactCount(Comment comment) {
    commentMapper.addPstReactCount(comment);
  }

  @Override
  public void subPstReactCount(Comment comment) {
    commentMapper.subPstReactCount(comment);
  }

  @Override
  public void addNgtReactCount(Comment comment) {
    commentMapper.addNgtReactCount(comment);
  }

  @Override
  public void subNgtReactCount(Comment comment) {
    commentMapper.subNgtReactCount(comment);
  }

  // Redis -> MySQL (pCount, nCount, total) by commentId
  @Override
  public void updateReactCount(int id, int pCount, int nCount) {
    Comment comment = this.findById(id);
    comment.setPCount(pCount);
    comment.setNCount(nCount);
    comment.setTotal(pCount - nCount);
    commentMapper.updateReactCount(comment);
  }

  @Override
  @CacheEvict(value = "ehcache")
  public void refresh() { }
}
