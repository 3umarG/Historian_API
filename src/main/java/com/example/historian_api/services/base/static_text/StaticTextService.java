package com.example.historian_api.services.base.static_text;



public interface StaticTextService {
    String getAboutText();
    String getPrivacyText();
    String updateAboutText(String aboutText);
    String updatePrivacyText(String privacyText);
}
