package com.pingsec.dev.repository;

import com.pingsec.dev.domain.Scenes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Scenes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScenesRepository extends JpaRepository<Scenes, Long> {

}
