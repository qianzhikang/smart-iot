package com.qzk.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzk.common.purview.domain.entity.Type;
import com.qzk.common.purview.mapper.TypeMapper;
import com.qzk.user.service.TypeService;
import org.springframework.stereotype.Service;

/**
* @author qianzhikang
* @description 针对表【t_type】的数据库操作Service实现
* @createDate 2022-12-15 15:41:36
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




