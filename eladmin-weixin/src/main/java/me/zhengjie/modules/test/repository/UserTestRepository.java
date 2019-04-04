package me.zhengjie.modules.test.repository;

import me.zhengjie.modules.test.domain.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-04-03
*/
public interface UserTestRepository extends JpaRepository<UserTest, Integer>, JpaSpecificationExecutor {
}