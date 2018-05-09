package campus.hackday.service;

import org.springframework.stereotype.Service;

@Service
public interface PstNgtCheckService {

  void check(int postId, int commentId, int userId, String react);

}
