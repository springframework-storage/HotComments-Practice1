package campus.hackday.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Comment implements Serializable {

  private int id;
  private int postId;
  private int userId;
  private String content;
  private int pCount;
  private int nCount;
  private int total;

}
