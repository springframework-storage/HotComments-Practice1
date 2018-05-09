package campus.hackday.service;

import org.springframework.stereotype.Service;

@Service
public interface PstReactService {

  void pstReact(int postId, int commentId, int userId);
  void insert(int commentId, int userId, int postId);
  void delete(int commentId, int userId);

//  PstReact findByCommentIdAndUserId(int commentId, int userId);
//  void deleteAll();
//  int countMemberByKey(int commentId);
//  Set<Integer> members(int commentId);
//  boolean isMember(int commentId, int userId);

}
