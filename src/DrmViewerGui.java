import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DrmViewerGui extends JFrame {

    private final JTextArea textArea;
    private final JLabel statusLabel;

    private File selectedFile;

    private final DeviceInfo deviceInfo;
    private final LicenseManager licenseManager;
    private final ProtectedFileManager protectedFileManager;
    private final License license;

    public DrmViewerGui(
            DeviceInfo deviceInfo,
            LicenseManager licenseManager,
            ProtectedFileManager protectedFileManager,
            License license
    ) {

        this.deviceInfo = deviceInfo;
        this.licenseManager = licenseManager;
        this.protectedFileManager = protectedFileManager;
        this.license = license;

        setTitle("Java DRM Viewer");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        statusLabel = new JLabel();

        JButton selectButton = new JButton("Select DRM file");
        JButton openButton = new JButton("Open file");

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(selectButton);
        topPanel.add(openButton);

        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);

        setLayout(new BorderLayout());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        updateLicenseStatus();

        selectButton.addActionListener(e -> selectFile());
        openButton.addActionListener(e -> openFile());
    }

    private void updateLicenseStatus() {

        String message =
                licenseManager.getValidationMessage(
                        license,
                        deviceInfo
                );

        statusLabel.setText(
                "License status: " + message
        );
    }

    private void selectFile() {

        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            selectedFile = fileChooser.getSelectedFile();

            textArea.setText(
                    "Selected file:\n"
                            + selectedFile.getAbsolutePath()
            );
        }
    }

    private void openFile() {

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a DRM file first",
                    "No file selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!licenseManager.isLicenseValid(
                license,
                deviceInfo
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    licenseManager.getValidationMessage(
                            license,
                            deviceInfo
                    ),
                    "Access denied",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            String content =
                    protectedFileManager.readProtectedFile(
                            selectedFile.getAbsolutePath(),
                            license,
                            deviceInfo,
                            licenseManager
                    );

            textArea.setText(content);

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to open protected file",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}