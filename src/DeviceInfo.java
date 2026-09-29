import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeviceInfo {

    public String getUserName() {
        return System.getProperty("user.name");

    }

    public String getOsName() {
        return System.getProperty("os.name");

    }

    public String getOsVersion() {
        return System.getProperty("os.version");
    }

    public String getOsArchitecture() {
        return System.getProperty("os.arch");
    }


    public String getDeviceFingerprint() {

        return getUserName() + "|" + getOsName() + "|" + getOsVersion() + "|" + getOsArchitecture();
    }

    public String getHashedFingerprint() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    getDeviceFingerprint().getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder hexString = new StringBuilder();

            for(byte b:hash){
                String hex =String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("SHA-256 algorithm is not available");
        }


    }
}