package campus.hackday.service;

import campus.hackday.dto.PstReact;
import org.springframework.stereotype.Service;

@Service
public interface PstReactService {

  PstReact findByCommentIdAndUserId(int commentId, int userId);

  void pstReact(int postId, int commentId, int userId) throws IllegalAccessException;

  void insert(int commentId, int userId, int postId);
  void delete(int commentId, int userId);
  void deleteAll();

}
