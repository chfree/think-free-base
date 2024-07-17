package com.cditer.free.quartz.job.commJob;

import cn.hutool.json.JSONUtil;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.quartz.job.BaseJob;
import com.cditer.free.quartz.logical.viewmodel.TaskExecResult;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public abstract class BatchCommonJob implements BaseJob {
    public static final String EXEC_SERVICE = "EXEC_SERVICE";
    public static final String EXEC_METHOD = "EXEC_METHOD";
    public static final String EXEC_PARAMETER = "EXEC_PARAMETER";
    public static final String TASK_NAME = "TASK_NAME";

    public TaskExecResult invoke(JobDataMap map) throws JobExecutionException {
        String json = null;
        try {
            json = JSONUtil.toJsonStr(map);
            String service = map.getString(EXEC_SERVICE);
            String method = map.getString(EXEC_METHOD);


            setBussinessMDC(method);

            Object obj = SpringContextUtils.getCurrentContext().getBean(service);
            Class<?> clazz = obj.getClass();

            Object invoke = null;
            try {
                Method mt = clazz.getMethod(method, Map.class);
                invoke = mt.invoke(obj, map);
            } catch (NoSuchMethodException ne) {
                Method mt = clazz.getMethod(method);
                invoke = mt.invoke(obj);
            }
            if (invoke instanceof TaskExecResult) {
                return (TaskExecResult) invoke;
            }
            return TaskExecResult.newResult(true);
        } catch (NoSuchMethodException ne) {
            log.error("execute job fail,no method find. " + json, ne);
            throw new JobExecutionException("execute job fail,no method find. " + ne.getMessage() + ";" + json, ne);
        } catch (InvocationTargetException e) {
            log.error("execute job fail,no target fail. " + json, e.getTargetException());
            throw new JobExecutionException("execute job fail,no target fail. " + e.getMessage() + ";" + json, e);
        } catch (Exception ex) {
            log.error("execute job fail. " + json, ex);
            throw new JobExecutionException("execute job fail. " + ex.getMessage() + ";" + json, ex);
        }
    }

    private static void setBussinessMDC(String method) {
        String bussiness = MDC.get("bussiness");
        if (!StringUtils.hasText(bussiness)) {
            MDC.put("bussiness", CommonUtils.getTraceId() + method);
        }
    }
}
