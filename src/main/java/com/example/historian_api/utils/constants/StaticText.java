package com.example.historian_api.utils.constants;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StaticText {
    private String aboutText;
    private String privacyText;
    private List<String> technicalSupportContactInfo;
    private String googlePlayLink;
    private String appStoreLink;
    public static StaticText getDefault() {
        return StaticText.builder()
                .aboutText("Your default About text")
                .privacyText("Your default Privacy text")
                .technicalSupportContactInfo(new ArrayList<>())
                .googlePlayLink("Your default google play app link")
                .appStoreLink("Your default app store app link")
                .build();
    }
}
