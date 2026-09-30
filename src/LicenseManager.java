import java.time.LocalDate;

public class LicenseManager {

    public boolean isLicenseValid(License license, DeviceInfo deviceInfo){
        if(!isLicenseKeyFormatValid(license.getLicenseKey())){
            return false;
        }
        if (isLicenseExpired(license)) {
            return false;
        }

        String currentFingerprint = deviceInfo.getHashedFingerprint();
        String licenseFingerprint = license.getDeviceFingerprint();

        return currentFingerprint.equals(licenseFingerprint);
    }

    public String getValidationMessage(License license, DeviceInfo deviceInfo){
        if(!isLicenseKeyFormatValid(license.getLicenseKey())){
            return "invalid License key format";
        }
        if (isLicenseExpired(license)) {
            return "License has expired";
        }

        if (!deviceInfo.getHashedFingerprint()
                .equals(license.getDeviceFingerprint())) {
            return "License is bound to another device";
        }

        return "License is valid";
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

        for(int i=4;i<licenseKey.length();i++){

            if(i==12){
                continue;
            }

            char currentCharacter = licenseKey.charAt(i);
            if(!isHexCharacter(currentCharacter)){
                return false;
            }
        }

        return true;


    }

    public boolean isHexCharacter(char c){
        return (c>='0' && c<='9') || (c >= 'A' && c<='F');
    }
    public boolean isLicenseExpired(License license) {

        LocalDate today = LocalDate.now();

        return today.isAfter(license.getExpirationDate());
    }



}
