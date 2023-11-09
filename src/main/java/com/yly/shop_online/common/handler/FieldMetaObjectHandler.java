package com.yly.shop_online.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yly.shop_online.common.constant.Constant;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //创建时间字段自动更新
        strictInsertFill(metaObject, Constant.CREATE_TIME, LocalDateTime.class,LocalDateTime.now());
        //更新时间字段自动更新
        strictInsertFill(metaObject, Constant.UPDATE_TIME, LocalDateTime.class,LocalDateTime.now());
        //逻辑删除字段自动更新
        strictInsertFill(metaObject,Constant.DELETED_FLAG,Integer.class,0);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时间字段自动更新
        strictInsertFill(metaObject, Constant.UPDATE_TIME, LocalDateTime.class,LocalDateTime.now());
    }
}
