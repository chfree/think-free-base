package com.cditer.free.common.data.logical;

import com.cditer.free.common.data.entity.model.CommonLog;
import com.cditer.free.common.data.entity.viewmodel.CommonLogSearch;
import com.cditer.free.common.data.entity.viewmodel.CommonLogView;
import com.cditer.free.data.dao.base.ISuperService;
import com.cditer.free.core.message.data.PagerModel;
import java.util.List;


/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-16 12:17:19
 * @comment     通用日志
 */

public interface ICommonLogService extends ISuperService<CommonLog>{

    /**
     * 根据搜索条件查询[通用日志]条数
     * @param search 搜索条件
     * @return [通用日志]条数
     */
    int queryCountBySearch(CommonLogSearch search);

    /**
     * 根据搜索条件分页查询[通用日志]对象集合
     * @param search 搜索条件
     * @param pagerModel 分页条件
     * @return [通用日志]对象集合
     */
    List<CommonLogView> queryListViewBySearch(CommonLogSearch search, PagerModel pagerModel);

    /**
     * 根据搜索条件查询[通用日志]对象
     * @param search 搜索条件
     * @return [通用日志]对象
     */
    CommonLogView queryModelViewBySearch(CommonLogSearch search);

    /**
     * 根据id查询[通用日志]对象
     * @param id 主键id
     * @return [通用日志]对象
     */
    CommonLogView queryModelViewById(String id);

    /**
     * 保存一个[通用日志]对象
     * @param commonLog [通用日志]对象
     * @return 是否成功
     */
    boolean saveCommonLog(CommonLog commonLog);

    void setLoginModel(CommonLog commonLog);

    void info(String bsnType,String bsnId,String shortMessage);

    void warn(String bsnType,String bsnId,String shortMessage);

    void debug(String bsnType,String bsnId,String shortMessage);

    void error(String bsnType,String bsnId,String shortMessage);
}
