package com.pingsec.dev.repository;

import com.pingsec.dev.domain.Images;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Images entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findAllByHashCodeEquals(String hashcode);
}
