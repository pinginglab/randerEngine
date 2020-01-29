package com.pingsec.dev.repository;

import com.pingsec.dev.domain.Networks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Networks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetworksRepository extends JpaRepository<Networks, Long> {

}
