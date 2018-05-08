package campus.hackday.redisService;

import org.springframework.stereotype.Service;

@Service
public interface PstNgtCheckService {

  void check(int commentId, int userId, String react);

}
