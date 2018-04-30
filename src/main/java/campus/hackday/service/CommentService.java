package campus.hackday.service;

import campus.hackday.dto.Comment;
import campus.hackday.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;

  public List<Comment> findAllByPostId(int postId) {
    return commentMapper.findAllByPostId(postId);
  }

}
