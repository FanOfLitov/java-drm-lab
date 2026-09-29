public class LicenseManager {

    public boolean isLicenseValid(License license, DeviceInfo deviceInfo){
        if(!isLicenseKeyFormatValid(license.getLicenseKey())){
            return false;
        }
        String currentFingerprint = deviceInfo.getHashedFingerprint();
        String licenseFingerprint = license.getDeviceFingerprint();

        return currentFingerprint.equals(licenseFingerprint);
    }

    public String getValidationMessage(License license, DeviceInfo deviceInfo){
        if(isLicenseKeyFormatValid(license.getLicenseKey())){
            return "invalid License key format";
        }

        if (!deviceInfo.getHashedFingerprint()
                .equals(license.getDeviceFingerprint())) {
            return "License is bound to another device";
        }

        return "License is bound to another device";
    }

    public boolean isLicenseKeyFormatValid(String licenseKey){
        if(licenseKey == null){
            return false;
        }
        if(licenseKey.length() !=17){
            return false;
        }

        if(!licenseKey.startsWith("DRM-")){
            return false;
        }

        if(licenseKey.charAt(12) !='-'){
            return false;
        }

        return true;
    }
}
