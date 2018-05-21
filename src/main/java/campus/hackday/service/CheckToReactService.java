package campus.hackday.service;

import campus.hackday.model.ReactParam;
import org.springframework.stereotype.Service;

@Service
public interface CheckToReactService {

  void checkToReact(int postId, int commentId, int userId, ReactParam reactParam);

}
