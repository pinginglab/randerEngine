package com.pingsec.dev.repository;

import com.pingsec.dev.domain.Ports;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortsRepository extends JpaRepository<Ports, Long> {

}
