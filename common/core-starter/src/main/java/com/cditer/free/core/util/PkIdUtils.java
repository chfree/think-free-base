package com.cditer.free.core.util;

import cn.hutool.core.util.IdUtil;
import com.cditer.free.core.enums.IdModeEnum;
import com.cditer.free.core.autoconfig.CoreBootConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-19 15:34
 * @comment
 */

@Slf4j
public class PkIdUtils {
    static CoreBootConfig coreBootConfig;

    public static String getId(){
        if(coreBootConfig == null){
            try {
                coreBootConfig = SpringContextUtils.getCurrentContext().getBean(CoreBootConfig.class);
            }catch (Exception ex){
                log.warn("无法获取CoreBootConfig配置信息");
            }
        }
        if(coreBootConfig==null){
            return IdUtil.randomUUID();
        }
        if(IdModeEnum.SNOW.getValue().equals(coreBootConfig.getIdMode())){
            return SnowFlakeIdUtils.nextId().toString();
        }
        return IdUtil.randomUUID();
    }
}

