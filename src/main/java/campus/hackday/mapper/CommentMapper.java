package campus.hackday.mapper;

import campus.hackday.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

  List<Comment> findAll();
  List<Comment> findAllByPostId(int postId);
  List<Comment> findAllByPostIdOrderByTotalDesc(int postId);
  Comment findById(int id);

  // 공감 +, -
  void addPstReactCount(Comment comment);
  void subPstReactCount(Comment comment);

  // 비공감 +, -
  void addNgtReactCount(Comment comment);
  void subNgtReactCount(Comment comment);

  // Redis -> MySQL
  void updateReactCount(Comment comment);

}
