package me.zhengjie.modules.test.service.query;

import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.test.domain.UserTest;
import me.zhengjie.modules.test.service.dto.UserTestDTO;
import me.zhengjie.modules.test.repository.UserTestRepository;
import me.zhengjie.modules.test.service.mapper.UserTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "userTest")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserTestQueryService {

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private UserTestMapper userTestMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UserTestDTO userTest, Pageable pageable){
        Page<UserTest> page = userTestRepository.findAll(new Spec(userTest),pageable);
        return PageUtil.toPage(page.map(userTestMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UserTestDTO userTest){
        return userTestMapper.toDto(userTestRepository.findAll(new Spec(userTest)));
    }

    class Spec implements Specification<UserTest> {

        private UserTestDTO userTest;

        public Spec(UserTestDTO userTest){
            this.userTest = userTest;
        }

        @Override
        public Predicate toPredicate(Root<UserTest> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

                if(!ObjectUtils.isEmpty(userTest.getName())){
                    /**
                    * 模糊
                    */
                    list.add(cb.like(root.get("name").as(String.class),"%"+userTest.getName()+"%"));
                }
                if(!ObjectUtils.isEmpty(userTest.getNickname())){
                    /**
                    * 模糊
                    */
                    list.add(cb.like(root.get("nickName").as(String.class),"%"+userTest.getNickname()+"%"));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}