import java.util.UUID;

public class LicenseKeyGenerator{
    public String generateKey(){
        String uuid = UUID.randomUUID().toString();

        String firstPart = uuid.substring(0,8).toUpperCase();
        String secondPart = uuid.substring(9,13).toUpperCase();

        return "DRM-" +firstPart+"-"+secondPart;

    }
}