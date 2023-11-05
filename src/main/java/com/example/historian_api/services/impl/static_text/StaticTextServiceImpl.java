package com.example.historian_api.services.impl.static_text;

import com.example.historian_api.services.base.static_text.StaticTextService;
import com.example.historian_api.utils.constants.StaticText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
