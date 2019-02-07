package com.muzhiyun.service.view;

import com.muzhiyun.service.entity.Goods;

public interface GoodsView extends View{

    void onSuccess(Goods goods);

    void onError(String results);
}
