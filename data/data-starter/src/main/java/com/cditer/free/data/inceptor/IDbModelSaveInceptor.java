package com.cditer.free.data.inceptor;

import com.cditer.free.core.message.data.IDbModel;

import java.util.List;

public interface IDbModelSaveInceptor {
    void saveAfter(List<? extends IDbModel> list);

    void saveBefore(List<? extends IDbModel> list);
}
