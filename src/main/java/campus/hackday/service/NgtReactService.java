package campus.hackday.service;

import org.springframework.stereotype.Service;

@Service
public interface NgtReactService {

  void ngtReact(int postId, int commentId, int userId);
  void insert(int commentId, int userId, int postId);
  void delete(int commentId, int userId);

//  NgtReact findByCommentIdAndUserId(int commentId, int userId);
//  void deleteAll();
//  int countMemberByKey(int commentId);
//  Set<Integer> members(int commentId);
//  boolean isMember(int commentId, int userId);

}
