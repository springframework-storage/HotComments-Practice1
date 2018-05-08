package campus.hackday.redisService;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RedisPstReactService {

  int countMemberByKey(int commentId);
  Set<Integer> members(int commentId);

  boolean isMember(int commentId, int userId);
  void insert(int commentId, int userId);

}
