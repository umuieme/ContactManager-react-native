import { NativeModules, PermissionsAndroid, Platform } from 'react-native';

export const ContactManager = {
    fetch: () => {
        // another way of implementing this
        if (Platform.OS === 'android') {
            return PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.READ_CONTACTS)
                .then((status) => {
                    if (status) {
                        return NativeModules.ContactReader.readContact();
                    }
                    return PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.READ_CONTACTS)
                        .then((granted) => {
                            if (granted === PermissionsAndroid.RESULTS.GRANTED) {
                                return NativeModules.ContactReader.readContact();
                            }
                            return Promise.reject('permission not granted');
                        });
                });
        }
        return Promise.reject('Not implemented');
    },
};
