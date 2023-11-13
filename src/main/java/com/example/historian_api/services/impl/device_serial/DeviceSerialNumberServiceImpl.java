package com.example.historian_api.services.impl.device_serial;

import com.example.historian_api.services.base.device_serial.DeviceSerialNumberService;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class DeviceSerialNumberServiceImpl implements DeviceSerialNumberService {
    @Override
    public String getDeviceSerialNumber() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            Process process;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec(new String[]{"wmic", "bios", "get", "serialnumber"});
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                process = Runtime.getRuntime().exec(new String[]{"bash", "-c", "ioreg -c IOPlatformExpertDevice | grep SerialNumber"});
            } else {
                return "Serial Number Retrieval Not Supported on this Operating System";
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                StringBuilder serialNumber = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    serialNumber.append(line.trim());
                }

                return serialNumber.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving serial number";
        }
    }


}
