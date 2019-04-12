package me.zhengjie.modules.test.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.test.domain.UserTest;
import me.zhengjie.modules.test.service.UserTestService;
import me.zhengjie.modules.test.service.dto.UserTestDTO;
import me.zhengjie.modules.test.service.query.UserTestQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author jie
* @date 2019-04-03
*/
@RestController
@RequestMapping("api")
public class UserTestController {

    @Autowired
    private UserTestService userTestService;

    @Autowired
    private UserTestQueryService userTestQueryService;

    private static final String ENTITY_NAME = "userTest";

    @Log("查询UserTest")
    @GetMapping(value = "/userTest")
    @PreAuthorize("hasAnyRole('ADMIN','WEIXIN_ALL')")
    public ResponseEntity getUserTests(UserTestDTO resources, Pageable pageable){
        return new ResponseEntity(userTestQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增UserTest")
    @PostMapping(value = "/userTest")
    @PreAuthorize("hasAnyRole('ADMIN','WEIXIN_ALL')")
    public ResponseEntity create(@Validated @RequestBody UserTest resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(userTestService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改UserTest")
    @PutMapping(value = "/userTest")
    @PreAuthorize("hasAnyRole('ADMIN','WEIXIN_ALL')")
    public ResponseEntity update(@Validated @RequestBody UserTest resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        userTestService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除UserTest")
    @DeleteMapping(value = "/userTest/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','WEIXIN_ALL')")
    public ResponseEntity delete(@PathVariable Integer id){
        userTestService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}