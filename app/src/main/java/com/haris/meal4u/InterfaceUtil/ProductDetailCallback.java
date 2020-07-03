package com.haris.meal4u.InterfaceUtil;

import com.haris.meal4u.ObjectUtil.AuthorObject;

public interface ProductDetailCallback {

    void onSelect(int parentPosition,boolean isSelected);

    void specialInstructionsListener(String instructions);


}
