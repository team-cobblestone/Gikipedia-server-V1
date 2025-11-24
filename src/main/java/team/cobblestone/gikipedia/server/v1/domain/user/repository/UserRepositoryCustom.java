package team.cobblestone.gikipedia.server.v1.domain.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import team.cobblestone.gikipedia.server.v1.domain.user.entity.User;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.UserRole;

public interface UserRepositoryCustom {

    Page<User> searchUsers(String keyword, UserRole role, Pageable pageable);

    List<User> findTopContributors(int limit);

}
