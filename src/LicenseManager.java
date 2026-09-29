public class LicenseManager {

    public boolean isLicenseValid(License license, DeviceInfo deviceInfo){
        String currentFingerprint = deviceInfo.getHashedFingerprint();
        String licenseFingerprint = license.getDeviceFingerprint();

        return currentFingerprint.equals(licenseFingerprint);
    }
}
