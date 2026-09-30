import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class LicenseFileManager {

    public void saveLicense(License license, String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("owner=" + license.getOwnerName() + "\n");
            writer.write("key=" + license.getLicenseKey() + "\n");
            writer.write("device=" + license.getDeviceFingerprint() + "\n");
            writer.write("expiration=" + license.getExpirationDate() + "\n");

        } catch (IOException e) {
            throw new RuntimeException("Failed to save license", e);
        }
    }

    public License loadLicense(String fileName) {

        String owner = null;
        String key = null;
        String device = null;
        LocalDate expirationDate = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("owner=")) {
                    owner = line.substring(6);
                }

                if (line.startsWith("key=")) {
                    key = line.substring(4);
                }

                if (line.startsWith("device=")) {
                    device = line.substring(7);
                }

                if (line.startsWith("expiration=")) {
                    expirationDate = LocalDate.parse(line.substring(11));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load license", e);
        }

        return new License(
                key,
                device,
                owner,
                expirationDate
        );
    }
}