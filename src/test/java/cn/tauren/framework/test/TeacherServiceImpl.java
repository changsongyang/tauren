/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.annotation.Inject;

/**
 * 
 * @author HuHui
 * @version $Id: TeacherServiceImpl.java, v 0.1 2017年11月17日 下午5:08:44 HuHui Exp $
 */
@Bean
public class TeacherServiceImpl implements TeacherService {

    @Inject
    private StudentService stuService;

    @Override
    public void tech() {
        System.out.println("TeacherService.tech()");
    }

}
