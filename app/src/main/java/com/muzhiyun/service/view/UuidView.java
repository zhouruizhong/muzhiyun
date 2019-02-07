package com.muzhiyun.service.view;

import com.muzhiyun.service.entity.Uuid;

public interface UuidView extends View {

    /**
     *
     * @param mUuid
     */
    void onSuccess(Uuid mUuid);

    /**
     *
     * @param result
     */
    void onError(String result);
}
