package campus.hackday.model;

import lombok.Data;

@Data
public class DefaultResponse {

  private Status status;
  private Object data;
  private String msg;

  public DefaultResponse() {
    this.data = null;
    this.msg = null;
    this.status = Status.FAIL;
  }

}
