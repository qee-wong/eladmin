package me.zhengjie.modules.test.service.impl;

import me.zhengjie.modules.test.domain.UserTest;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.test.repository.UserTestRepository;
import me.zhengjie.modules.test.service.UserTestService;
import me.zhengjie.modules.test.service.dto.UserTestDTO;
import me.zhengjie.modules.test.service.mapper.UserTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
* @author jie
* @date 2019-04-03
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserTestServiceImpl implements UserTestService {

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private UserTestMapper userTestMapper;

    @Override
    public UserTestDTO findById(Integer id) {
        Optional<UserTest> userTest = userTestRepository.findById(id);
        ValidationUtil.isNull(userTest,"UserTest","id",id);
        return userTestMapper.toDto(userTest.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserTestDTO create(UserTest resources) {
        return userTestMapper.toDto(userTestRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserTest resources) {
        Optional<UserTest> optionalUserTest = userTestRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalUserTest,"UserTest","id",resources.getId());

        UserTest userTest = optionalUserTest.get();
        // 此处需自己修改
        resources.setId(userTest.getId());
        userTestRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        userTestRepository.deleteById(id);
    }
}