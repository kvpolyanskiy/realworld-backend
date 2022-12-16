package kv.polyanskiy.realworld.repositories;

import kv.polyanskiy.realworld.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
  Tag findByName(String name);
}
