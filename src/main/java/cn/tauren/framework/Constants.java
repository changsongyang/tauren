/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework;

/**
 * 一些常量
 * @author HuHui
 * @version $Id: Constants.java, v 0.1 2017年11月16日 上午11:24:26 HuHui Exp $
 */
public interface Constants {

    /** 默认配置文件名 */
    static final String DEFAULT_CONFIG_NAME    = "tauren.config";

    /************************配置文件字段名称***************************/
    /** 工程扫码包路径字段 */
    static final String SCAN_PACKAGE_FIELD     = "scan.package";

    /** jsp目录字段 */
    static final String JSP_PATH_FIELD         = "jsp.path";

    /** 主页字段 */
    static final String INDEX_FIELD            = "index";
    /********end********/

    /************************配置文件字段默认值***************************/

    /** 默认jsp目录值 */
    static final String DEFAULT_JSP_PATH_VALUE = "/WEB-INF/jsp/";

    /** 默认主页值 */
    static final String DEFAULT_INDEX          = "index.jsp";
    /********end********/

    /************************Tauren框架使用常量***************************/

    static final String FILE_DOT               = ".";

    /** 字符编码 */
    static final String UTF8                   = "UTF-8";

    /** jsp文件后缀 */
    static final String SUFFIX_JSP             = ".jsp";

    /** 主页 */
    static final String INDEX                  = ConfigFileReader.getIndex();

    /** jsp目录值 */
    static final String JSP_PATH_VALUE         = ConfigFileReader.getJspPath();
    /********end********/

}
