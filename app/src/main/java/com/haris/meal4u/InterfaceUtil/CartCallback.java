package com.haris.meal4u.InterfaceUtil;

public interface CartCallback {

    void onItemDeleteListener(int position);

    void onItemQuantityListener(int position , boolean isIncrease);

  /*  void onContactInformationChangeListener();

    void onDeliveryDetailChangeListener();

    void onDeliveryTimeChangeListener();

    void onPaymentMethodChangeListener();

    void onPlaceOrderListener();
*/

}
