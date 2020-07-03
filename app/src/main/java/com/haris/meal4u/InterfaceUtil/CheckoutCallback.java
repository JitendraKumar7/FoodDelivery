package com.haris.meal4u.InterfaceUtil;

import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.ObjectUtil.BillingObject;
import com.haris.meal4u.ObjectUtil.ScheduleObject;

public interface CheckoutCallback {

    void onAddressChangeListener(AddressObject addressObject);

    void onBillingChangeListener(BillingObject billingObject);

    void onDeliveryChangeListener(ScheduleObject scheduleObject);

}
