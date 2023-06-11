package com.hnust.usercentral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnust.usercentral.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author HUAWEI
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-05-19 12:34:23
* @Entity com.hnust.usercentral.model.domain.User.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




