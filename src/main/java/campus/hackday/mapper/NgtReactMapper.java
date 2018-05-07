package campus.hackday.mapper;

import campus.hackday.dto.NgtReact;
import campus.hackday.model.ReactModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NgtReactMapper {

  NgtReact findById(int id);

  NgtReact findByCommentIdAndUserId(@Param("commentId") int commentId, @Param("userId") int userId);

  void insert(ReactModel reactModel);
  void delete(int id);
  void deleteAll();

}
