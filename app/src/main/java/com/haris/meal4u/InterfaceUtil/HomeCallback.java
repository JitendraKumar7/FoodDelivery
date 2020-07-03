package com.haris.meal4u.InterfaceUtil;

import com.haris.meal4u.ConstantUtil.Constant;

public interface HomeCallback {

    void onSelect(int parentPosition,int childPosition);

    void onMore(Constant.DATA_TYPE dataType);

    void switchLayout(int layout,boolean isSwitchLayout);

    void onFavourite(int position,boolean isFavourite);

}
