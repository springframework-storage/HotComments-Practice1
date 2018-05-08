package campus.hackday.service;

import campus.hackday.dto.NgtReact;
import org.springframework.stereotype.Service;

@Service
public interface NgtReactService {

  NgtReact findByCommentIdAndUserId(int commentId, int userId);

  void ngtReact(int postId, int commentId, int userId) throws IllegalAccessException;

  void insert(int commentId, int userId, int postId);
  void delete(int commentId, int userId);
  void deleteAll();

}
