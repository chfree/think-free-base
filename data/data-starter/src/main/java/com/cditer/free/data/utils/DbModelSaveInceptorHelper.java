package com.cditer.free.data.utils;

import com.cditer.free.core.exception.BizException;
import com.cditer.free.core.message.data.IDbModel;
import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.data.boot.autoconfig.DataBootConfig;
import com.cditer.free.data.inceptor.IDbModelSaveInceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DbModelSaveInceptorHelper {
    @Autowired
    DataBootConfig dataBootConfig;

    public void dbModelSaveBefore(List<? extends IDbModel> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        Map<String, IDbModelSaveInceptor> beans = SpringContextUtils.getBeans(IDbModelSaveInceptor.class);
        if(CollectionUtils.isEmpty(beans)){
            return;
        }

        for (Map.Entry<String, IDbModelSaveInceptor> entry : beans.entrySet()) {
            IDbModelSaveInceptor dbModelSaveInceptor = entry.getValue();
            try {
                dbModelSaveInceptor.saveBefore(list);
            }catch (Exception ex){
                if(dataBootConfig.isDbModelSaveErrorBreak()){
                    throw new BizException(997, "dbModelSaveBefore is error", ex);
                }else{
                    log.error("dbModelSaveBefore is error", ex);
                }
            }
        }
    }

    public void dbModelSaveAfter(List<? extends IDbModel> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        Map<String, IDbModelSaveInceptor> beans = SpringContextUtils.getBeans(IDbModelSaveInceptor.class);
        if(CollectionUtils.isEmpty(beans)){
            return;
        }

        for (Map.Entry<String, IDbModelSaveInceptor> entry : beans.entrySet()) {
            IDbModelSaveInceptor dbModelSaveInceptor = entry.getValue();
            try {
                dbModelSaveInceptor.saveAfter(list);
            }catch (Exception ex){
                if(dataBootConfig.isDbModelSaveErrorBreak()){
                    throw new BizException(997, "dbModelSaveAfter is error", ex);
                }else{
                    log.error("dbModelSaveAfter is error", ex);
                }
            }
        }
    }
}
