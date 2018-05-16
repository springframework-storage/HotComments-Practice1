package campus.hackday.controller;

import campus.hackday.mysqlServiceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class CacheController {

  @Autowired
  private CommentServiceImpl commentService;

  @GetMapping("refresh")
  public String refresh() {
    commentService.refresh();
    return "cache refresh";
  }

}
