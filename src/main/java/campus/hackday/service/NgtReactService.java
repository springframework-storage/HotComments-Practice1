package campus.hackday.service;

import org.springframework.stereotype.Service;

@Service
public interface NgtReactService {

  void ngtReact(int postId, int commentId, int userId);

}
