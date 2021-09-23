package com.cditer.free.core.util;

import cn.hutool.core.util.IdUtil;
import com.cditer.free.core.enums.IdModeEnum;
import com.cditer.free.core.autoconfig.CoreBootConfig;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-19 15:34
 * @comment
 */

public class PkIdUtils {
    static CoreBootConfig coreBootConfig;

    public static String getId(){
        if(coreBootConfig == null){
            coreBootConfig = SpringContextUtils.getCurrentContext().getBean(CoreBootConfig.class);
        }
        if(IdModeEnum.SNOW.getValue().equals(coreBootConfig.getIdMode())){
            return SnowFlakeIdUtils.nextId().toString();
        }
        return IdUtil.randomUUID();
    }
}

