package com.pingsec.dev.repository;

import com.pingsec.dev.domain.Tasks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Tasks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    // 这一项未空证明没有生成docker-compose
    // 系统将根据需求进行compose生成
    List<Tasks> findAllByContentNull();
    // 生成环境不成功则数据为空
    List<Tasks> findAllByBuildTimeNull();
}
