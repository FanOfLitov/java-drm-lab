import javax.swing.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        DeviceInfo deviceInfo = new DeviceInfo();

        LicenseKeyGenerator keyGenerator =
                new LicenseKeyGenerator();

        String generatedKey =
                keyGenerator.generateKey();

        License license = new License(
                generatedKey,
                deviceInfo.getHashedFingerprint(),
                "Ivan",
                LocalDate.now().plusDays(30)
        );

        LicenseFileManager licenseFileManager =
                new LicenseFileManager();

        licenseFileManager.saveLicense(
                license,
                "license.txt"
        );

        License loadedLicense =
                licenseFileManager.loadLicense(
                        "license.txt"
                );

        LicenseManager licenseManager =
                new LicenseManager();

        ProtectedFileManager protectedFileManager =
                new ProtectedFileManager();

        protectedFileManager.createProtectedFile(
                "protected.txt",
                "document.drm",
                loadedLicense
        );
        SwingUtilities.invokeLater(() -> {

            DrmViewerGui gui =
                    new DrmViewerGui(
                            deviceInfo,
                            licenseManager,
                            protectedFileManager,
                            loadedLicense
                    );

            gui.setVisible(true);
        });
    }
}