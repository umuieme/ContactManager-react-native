package com.contactmanager;

import android.app.Activity;
import android.content.ActivityNotFoundException;

import com.aabumu.contactmanager.Contact;
import com.aabumu.contactmanager.ContactManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.List;

public class ContactReaderModule extends ReactContextBaseJavaModule {

    private static final String MODULE_NAME = "ContactReader";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "phone_number";

    public ContactReaderModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void readContact(Promise promise) {
        Activity activity = getCurrentActivity();

        if (activity == null) {
            promise.reject(new ActivityNotFoundException());
            return;
        }
        ContactManager contactManager = new ContactManager(getReactApplicationContext());
        promise.resolve(transferToWritableArray(contactManager.fetchContact()));
    }

    private WritableArray transferToWritableArray(List<Contact> contactList) {
        WritableArray contactArray = new WritableNativeArray();

        for (Contact contact : contactList) {
            WritableMap contactMap = new WritableNativeMap();

            contactMap.putString(KEY_NAME, contact.getName());
            WritableArray phoneArray = new WritableNativeArray();
            for (int i = 0; i < contact.getPhoneNumber().size(); i++) {
                phoneArray.pushString(contact.getPhoneNumber().get(i));
            }
            contactMap.putArray(KEY_PHONE_NUMBER, phoneArray);
            contactArray.pushMap(contactMap);
        }
        return contactArray;
    }
}