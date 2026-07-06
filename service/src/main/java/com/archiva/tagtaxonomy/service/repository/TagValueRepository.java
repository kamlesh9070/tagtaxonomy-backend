package com.archiva.tagtaxonomy.service.repository;

import com.archiva.tagtaxonomy.service.entity.TagValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagValueRepository extends JpaRepository<TagValue, Long> {
    Optional<TagValue> findByPublicId(UUID publicId);

    List<TagValue> findByTagTypeIdAndParentIsNull(Long tagTypeId);

    List<TagValue> findByTagTypeId(Long tagTypeId);
}
