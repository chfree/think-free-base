package com.tennetcn.free.core.util;

import cn.hutool.core.util.IdUtil;
import com.tennetcn.free.core.boot.autoconfig.CoreBootConfig;
import com.tennetcn.free.core.enums.IdModeEnum;

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
        if(IdModeEnum.SNOW.getKey().equals(coreBootConfig.getIdMode())){
            return SnowFlakeIdUtils.nextId().toString();
        }
        return IdUtil.randomUUID();
    }
}

