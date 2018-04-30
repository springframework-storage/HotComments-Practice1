package campus.hackday.mapper;

import campus.hackday.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

  List<Comment> findAllByPostId(int postId);

}
