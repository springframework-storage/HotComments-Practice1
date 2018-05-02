package campus.hackday.mapper;

import campus.hackday.dto.PstReact;
import campus.hackday.model.ReactModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PstReactMapper {

  PstReact findById(int id);

  PstReact findByCommentIdAndUserId(@Param("commentId") int commentId, @Param("userId") int userId);

  void insert(ReactModel reactModel);
  void delete(int id);

}
