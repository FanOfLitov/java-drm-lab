public class License {

    private String licenseKey;
    private String deviceFingerprint;
    private String ownerName;

    public License(String licenseKey, String deviceFingerprint, String ownerName){
        this.licenseKey=licenseKey;
        this.deviceFingerprint = deviceFingerprint;
        this.ownerName = ownerName;
    }

    public String getLicenseKey(){
        return licenseKey;
    }

    public String getDeviceFingerprint(){
        return deviceFingerprint;
    }

    public String getOwnerName(){
        return ownerName;
    }


}
