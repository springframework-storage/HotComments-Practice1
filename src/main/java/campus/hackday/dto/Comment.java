package campus.hackday.dto;

import lombok.Data;

@Data
public class Comment {

  private int id;
  private int userId;
  private int postId;
  private String content;
  private int pstReactCount;
  private int ngtReactCount;

}
