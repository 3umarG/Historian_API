package com.example.historian_api.services.impl.static_text;

import com.example.historian_api.dtos.responses.StaticTextResponseDto;
import com.example.historian_api.services.base.static_text.StaticTextService;
import com.example.historian_api.utils.constants.StaticText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticTextServiceImpl implements StaticTextService {

    private final StaticText staticText;

    @Override
    public String getAboutText() {
        return staticText.getAboutText();
    }
    @Override

    public String getPrivacyText() {
        return staticText.getPrivacyText();
    }
    @Override

    public String updateAboutText(String aboutText) {
        try{
            if(!aboutText.isEmpty()&&!aboutText.equals(staticText.getAboutText())){
                staticText.setAboutText(aboutText);
                return "Updated Successfully";
            }
                return "You must enter a new about text";
        }catch (Exception e){
            return "Something went wrong : "+e.getMessage();
        }
    }

    @Override
    public String updateGooglePlayLink(String googlePlayLink) {
        try{
            if(!googlePlayLink.isEmpty()&&!googlePlayLink.equals(staticText.getGooglePlayLink())){
                staticText.setGooglePlayLink(googlePlayLink);
                return "Updated Successfully";
            }
            return "You must enter a new google play link";
        }catch (Exception e){
            return "Something went wrong : "+e.getMessage();
        }
    }

    @Override
    public String updateAppStoreLink(String appStoreLink) {
        try{
            if(!appStoreLink.isEmpty()&&!appStoreLink.equals(staticText.getAppStoreLink())){
                staticText.setAppStoreLink(appStoreLink);
                return "Updated Successfully";
            }
            return "You must enter a new app store link";
        }catch (Exception e){
            return "Something went wrong : "+e.getMessage();
        }
    }

    @Override
    public String updateVodafoneCashNumber(String vodafoneCashNumber) {
        try{
            if(!vodafoneCashNumber.isEmpty()&&!vodafoneCashNumber.equals(staticText.getVodafoneCash())){
                staticText.setVodafoneCash(vodafoneCashNumber);
                return "Updated Successfully";
            }
            return "You must enter a new vodafone cash number";
        }catch (Exception e){
            return "Something went wrong : "+e.getMessage();
        }
    }

    @Override
    public List<String> getTechnicalSupportContactInfo() {
        return staticText.getTechnicalSupportContactInfo();
    }

    @Override
    public StaticTextResponseDto getAllStaticText() {
        return StaticTextResponseDto
                .builder()
                .aboutText(staticText.getAboutText())
                .privacyText(staticText.getPrivacyText())
                .appStoreLink(staticText.getAppStoreLink())
                .googlePlayLink(staticText.getGooglePlayLink())
                .vodafoneCash(staticText.getVodafoneCash())
                .technicalSupportContactsInfo(staticText.getTechnicalSupportContactInfo())
                .build();
    }

    @Override
    public String addTechnicalSupportNumber(String technicalSupportContactInfo) {
        try {
            if (!technicalSupportContactInfo.isEmpty()) {
                List<String> contactInfo = staticText.getTechnicalSupportContactInfo();

                if (contactInfo == null) {
                    contactInfo = new ArrayList<>();
                    staticText.setTechnicalSupportContactInfo(contactInfo);
                }

                if (!contactInfo.contains(technicalSupportContactInfo)) {
                    contactInfo.add(technicalSupportContactInfo);
                    return "Added Successfully";
                }
                return "Technical support contact info already exists.";
            }
            return "You must enter a new technical support contact info";
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    @Override

    public String updatePrivacyText(String privacyText) {
        try{
            if(!privacyText.isEmpty()&&!privacyText.equals(staticText.getPrivacyText())){
                staticText.setPrivacyText(privacyText);
                return "Updated Successfully";
            }
            return "You must enter a new privacy text";
        }catch (Exception e){
            return "Something went wrong : "+e.getMessage();
        }
    }
}
