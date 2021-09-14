package com.cditer.free.data.service.impl;

import cn.hutool.core.date.DateUtil;
import com.cditer.free.data.dao.ISequenceDao;
import com.cditer.free.data.service.ISequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 18:00
 * @comment
 */

@Component
public class SequenceServiceImpl implements ISequenceService {

    @Autowired
    ISequenceDao sequenceDao;

    @Override
    public int getSeq(String seqName) {
        return sequenceDao.getSeq(seqName);
    }

    @Override
    public String getSeq(String seqName, String prefix) {
        return prefix + getSeq(seqName);
    }

    @Override
    public String getDateSeq(String seqName, String prefix) {
        return prefix + DateUtil.format(DateUtil.date(),"yyyyMMdd") + getSeq(seqName);
    }
}
