package com.rch.etawah.InterfaceUtil;

import com.rch.etawah.ObjectUtil.AddressObject;
import com.rch.etawah.ObjectUtil.BillingObject;
import com.rch.etawah.ObjectUtil.ScheduleObject;

public interface CheckoutCallback {

    void onAddressChangeListener(AddressObject addressObject);

    void onBillingChangeListener(BillingObject billingObject);

    void onDeliveryChangeListener(ScheduleObject scheduleObject);

}
