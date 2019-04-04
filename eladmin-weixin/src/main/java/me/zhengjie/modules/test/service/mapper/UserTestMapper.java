package me.zhengjie.modules.test.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.test.domain.UserTest;
import me.zhengjie.modules.test.service.dto.UserTestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-04-03
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTestMapper extends EntityMapper<UserTestDTO, UserTest> {

}