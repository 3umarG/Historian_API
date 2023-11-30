package com.example.historian_api.services.base.static_text;


import com.example.historian_api.dtos.responses.StaticTextResponseDto;

import java.util.List;

public interface StaticTextService {
    String getAboutText();
    String getPrivacyText();
    String updateAboutText(String aboutText);
    String updateGooglePlayLink(String googlePlayLink);
    String updateAppStoreLink(String appStoreLink);
    String updateVodafoneCashNumber(String vodafoneCashNumber);
    List<String> getTechnicalSupportContactInfo();
    StaticTextResponseDto getAllStaticText();
    String addTechnicalSupportNumber(String technicalSupportContactInfo);
    String updatePrivacyText(String privacyText);
}
