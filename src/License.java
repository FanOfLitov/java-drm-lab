import java.time.LocalDate;

public class License {

    private String licenseKey;
    private String deviceFingerprint;
    private String ownerName;
    private LocalDate expirationDate;

    public License(
            String licenseKey,
            String deviceFingerprint,
            String ownerName,
            LocalDate expirationDate
    ) {
        this.licenseKey = licenseKey;
        this.deviceFingerprint = deviceFingerprint;
        this.ownerName = ownerName;
        this.expirationDate = expirationDate;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}