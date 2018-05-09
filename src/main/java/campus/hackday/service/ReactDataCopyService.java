package campus.hackday.service;

import org.springframework.stereotype.Service;

@Service
public interface ReactDataCopyService {

  void updateReactCount(int postId);
  void updatePstReact(int postId);
  void updateNgtReact(int postId);

}
