package com.pingsec.dev.repository;

import com.pingsec.dev.domain.App;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the App entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRepository extends JpaRepository<App, Long> {

}
