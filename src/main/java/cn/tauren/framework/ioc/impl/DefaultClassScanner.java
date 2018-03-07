/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.Constants;
import cn.tauren.framework.ioc.api.ClassScanner;
import cn.tauren.framework.util.AssertUtil;
import cn.tauren.framework.util.ClassUtil;

/**
 * 类扫描器
 * <p>当<code>tauren</code>框架启动时,会扫描客户端包中所有的类</p>
 * @author HuHui
 * @version $Id: DefaultClassScanner.java, v 0.1 2017年11月15日 下午8:29:56 HuHui Exp $
 */
public final class DefaultClassScanner implements ClassScanner {

    private static final String FILE_PROTOCOL = "file";

    private static final String FILE_SUFFIX   = ".class";

    private List<Class<?>>      classes       = new ArrayList<Class<?>>();

    private final String        pkgName;

    private Object              lock          = new Object();

    public DefaultClassScanner(String pkgName) {
        this.pkgName = pkgName;
    }

    /**
     * 返回包名路径下所有的类
     * @return         类集合
     */
    @Override
    public List<Class<?>> getClasses() {
        AssertUtil.assertNotBlank(pkgName, "包路径不能为空");
        synchronized (lock) {
            if (CollectionUtils.isNotEmpty(classes)) {
                return classes;
            }
            //对包名进行替换 .==>/
            String pkgDir = pkgName.replace(".", "/");
            //定义一个枚举集合
            Enumeration<URL> dirs;
            try {
                dirs = Thread.currentThread().getContextClassLoader().getResources(pkgDir);
                while (dirs.hasMoreElements()) {
                    URL url = dirs.nextElement();
                    String protocol = url.getProtocol();
                    if (StringUtils.equals(FILE_PROTOCOL, protocol)) { //如果是文件
                        //获取包的物理路径
                        String pkgPath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.displayName());
                        //扫描包下的文件并放入集合
                        addClassesToList(pkgName, pkgPath, classes);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("扫描类失败", e);
            }

            return classes;
        }
    }

    /**
     * 根据注解获取类
     * @return  结果不包含接口或抽象类
     */
    @Override
    public List<Class<?>> getClassesByAnnotation(Class<? extends Annotation> anno) {
        List<Class<?>> classesByAnno = new ArrayList<Class<?>>();
        List<Class<?>> classes = getClasses();
        if (CollectionUtils.isNotEmpty(classes)) {
            for (Class<?> _clazz : classes) {
                if (_clazz.isAnnotationPresent(anno) && !ClassUtil.isInterfaceOrAbstract(_clazz)) {
                    classesByAnno.add(_clazz);
                }
            }
        }

        return classesByAnno;
    }

    /**
     * 根据接口/父类获取其对应实现类/子类
     * @param superClass
     * @return 结果不包含接口或抽象类
     */
    @Override
    public List<Class<?>> getClassesBySuper(Class<?> superClass) {
        List<Class<?>> classesBySuper = new ArrayList<Class<?>>();
        List<Class<?>> classes = getClasses();
        if (CollectionUtils.isNotEmpty(classes)) {
            for (Class<?> _clazz : classes) {
                if (superClass.isAssignableFrom(_clazz) && !ClassUtil.isInterfaceOrAbstract(_clazz)) {
                    classesBySuper.add(_clazz);
                }
            }
        }

        return classesBySuper;
    }

    /**
     * 递归遍历
     * @param pkgName  包名
     * @param pkgPath  包的物理路径
     * @param classes  类的集合
     */
    private void addClassesToList(String pkgName, String pkgPath, List<Class<?>> classes) {
        File pkgFile = new File(pkgPath);

        //如果文件不存在或不是目录则直接返回
        if (!pkgFile.exists() || !pkgFile.isDirectory()) {
            return;
        }

        //如果存在则获取该目录下所有文件和目录
        File[] files = pkgFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(FILE_SUFFIX);
            }
        });

        for (File file : files) {
            if (file.isDirectory()) { //如果是目录则递归遍历
                String subPkgName = pkgName + Constants.FILE_DOT + file.getName();
                addClassesToList(subPkgName, file.getAbsolutePath(), classes);
            } else {//处理class文件
                String className = file.getName().substring(0, file.getName().length() - 6);//去除.class后缀
                String classFullName = pkgName + Constants.FILE_DOT + className;
                try {
                    classes.add(ClassUtils.getClass(classFullName, false));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("无法找到类", e);
                }
            }
        }
    }

}
