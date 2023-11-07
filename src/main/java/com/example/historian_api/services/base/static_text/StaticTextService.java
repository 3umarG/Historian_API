package com.example.historian_api.services.base.static_text;



public interface StaticTextService {
    String getAboutText();
    String getPrivacyText();
    String updateAboutText(String aboutText);
    String getTechnicalSupportContactInfo();
    String updateTechnicalSupportContactInfo(String technicalSupportContactInfo);
    String updatePrivacyText(String privacyText);
}
