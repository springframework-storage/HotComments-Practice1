package campus.hackday.service;

import org.springframework.stereotype.Service;

@Service
public interface PstReactService {

  void pstReact(int postId, int commentId, int userId);

}
