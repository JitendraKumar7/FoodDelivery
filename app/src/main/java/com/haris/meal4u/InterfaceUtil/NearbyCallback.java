package com.haris.meal4u.InterfaceUtil;

import com.haris.meal4u.ObjectUtil.AuthorObject;

public interface NearbyCallback {

    void onSelect(int position);

    void onFavourite(int position,boolean isFavourite);

}
