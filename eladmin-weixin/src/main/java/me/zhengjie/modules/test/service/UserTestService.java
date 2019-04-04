package me.zhengjie.modules.test.service;

import me.zhengjie.modules.test.domain.UserTest;
import me.zhengjie.modules.test.service.dto.UserTestDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author jie
* @date 2019-04-03
*/
@CacheConfig(cacheNames = "userTest")
public interface UserTestService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserTestDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UserTestDTO create(UserTest resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(UserTest resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Integer id);
}