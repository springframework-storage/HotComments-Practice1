package campus.hackday.service;

import campus.hackday.dto.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

  List<Comment> findAllByPostId(int postId);
  List<Comment> findAllByPostIdOrderByTotalDesc(int postId);
  Comment findById(int id);

  void addPstReactCount(Comment comment);
  void subPstReactCount(Comment comment);

  void addNgtReactCount(Comment comment);
  void subNgtReactCount(Comment comment);

  void updateReactCount(int id, int pCount, int nCount);

}
