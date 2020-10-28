package com.tennetcn.free.data.service;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 17:59
 * @comment
 */

public interface ISequenceService {
    int getSeq(String seqName);

    String getSeq(String seqName,String prefix);

    String getDateSeq(String seqName,String prefix);
}
