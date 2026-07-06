package com.archiva.tagtaxonomy.service.repository;

import com.archiva.tagtaxonomy.service.entity.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagTypeRepository extends JpaRepository<TagType, Long> {
    Optional<TagType> findByPublicId(UUID publicId);

    boolean existsByNameIgnoreCase(String name);

    Optional<TagType> findByNameIgnoreCase(String name);
}
