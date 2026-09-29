

public class Main {

    public static void main(String[] args) {

        DeviceInfo deviceInfo = new DeviceInfo();



        System.out.println("DRM system started");

        System.out.println("User: " + deviceInfo.getUserName());
        System.out.println("OS: " +deviceInfo.getOsName());
        System.out.println("OS version: " + deviceInfo.getOsVersion());
        System.out.println("Architecture: " + deviceInfo.getOsArchitecture());
        System.out.println("Fingerprint: "+deviceInfo.getDeviceFingerprint());
        System.out.println("Hashed fingerprint: " + deviceInfo.getHashedFingerprint() );


        LicenseKeyGenerator keyGenerator = new LicenseKeyGenerator();

        String generatedKey = keyGenerator.generateKey();


        License license = new License(
                generatedKey,
                deviceInfo.getHashedFingerprint(),
                "Ivan"
        );
        System.out.println("License owner: " + license.getOwnerName());
        System.out.println("License key: " + license.getLicenseKey());
        System.out.println("License device: " + license.getDeviceFingerprint());



        LicenseManager licenseManager = new LicenseManager();
        boolean isValid = licenseManager.isLicenseValid(license, deviceInfo);

        System.out.println("License valid: " + isValid);
        System.out.println("Status: " +licenseManager.getValidationMessage(license, deviceInfo));







    }
}