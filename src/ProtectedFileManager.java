import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import java.util.Base64;

public class ProtectedFileManager {


    private static final int IV_LENGTH = 12;


    private static final int TAG_LENGTH = 128;

    public void createProtectedFile(
            String sourceFile,
            String protectedFile,
            License license
    ) {

        try {

            String content = Files.readString(
                    Path.of(sourceFile),
                    StandardCharsets.UTF_8
            );

            SecretKeySpec key = createEncryptionKey(license);


            byte[] iv = new byte[IV_LENGTH];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);


            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(TAG_LENGTH, iv);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    gcmSpec
            );


            byte[] encryptedData = cipher.doFinal(
                    content.getBytes(StandardCharsets.UTF_8)
            );


            String encodedIv =
                    Base64.getEncoder().encodeToString(iv);

            String encodedData =
                    Base64.getEncoder().encodeToString(encryptedData);

            String drmContent =
                    "IV=" + encodedIv + "\n"
                            + "DATA=" + encodedData;

            Files.writeString(
                    Path.of(protectedFile),
                    drmContent,
                    StandardCharsets.UTF_8
            );

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(
                    "Failed to create protected file",
                    e
            );
        }
    }


    public String readProtectedFile(
            String protectedFile,
            License license,
            DeviceInfo deviceInfo,
            LicenseManager licenseManager
    ) {


        if (!licenseManager.isLicenseValid(license, deviceInfo)) {
            return "Access denied: "
                    + licenseManager.getValidationMessage(
                    license,
                    deviceInfo
            );
        }

        try {
            String content = Files.readString(
                    Path.of(protectedFile),
                    StandardCharsets.UTF_8
            );

            String[] lines = content.split("\n");

            String encodedIv =
                    lines[0].substring(3);

            String encodedData =
                    lines[1].substring(5);

            byte[] iv =
                    Base64.getDecoder().decode(encodedIv);

            byte[] encryptedData =
                    Base64.getDecoder().decode(encodedData);

            SecretKeySpec key =
                    createEncryptionKey(license);

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(TAG_LENGTH, iv);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    gcmSpec
            );

            byte[] decryptedData =
                    cipher.doFinal(encryptedData);

            return new String(
                    decryptedData,
                    StandardCharsets.UTF_8
            );

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(
                    "Failed to decrypt protected file",
                    e
            );
        }
    }


    private SecretKeySpec createEncryptionKey(
            License license
    ) throws GeneralSecurityException {

        String keyMaterial =
                license.getLicenseKey()
                        + "|"
                        + license.getDeviceFingerprint();

        MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

        byte[] keyBytes = digest.digest(
                keyMaterial.getBytes(StandardCharsets.UTF_8)
        );

        return new SecretKeySpec(
                keyBytes,
                "AES"
        );
    }
}