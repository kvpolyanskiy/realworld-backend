package kv.polyanskiy.realworld.repositories;

import kv.polyanskiy.realworld.domain.Following;
import kv.polyanskiy.realworld.domain.FollowingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, FollowingId> {

}
