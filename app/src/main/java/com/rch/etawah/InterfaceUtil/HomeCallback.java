package com.rch.etawah.InterfaceUtil;

import com.rch.etawah.ConstantUtil.Constant;

public interface HomeCallback {

    void onSelect(int parentPosition,int childPosition);

    void onMore(Constant.DATA_TYPE dataType);

    void switchLayout(int layout,boolean isSwitchLayout);

    void onFavourite(int position,boolean isFavourite);

}
