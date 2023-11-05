package com.example.historian_api.config;

import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.utils.constants.StaticText;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final StudentsRepository studentsRepository;
    private final TeachersRepository teachersRepository;

    @Bean(name = "studentsDetailsService")
    public UserDetailsService studentsDetailsService() {
        return phone -> studentsRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("There is no User with that phone number !!"));
    }

    @Bean(name = "instructorsDetailsService")
    public UserDetailsService instructorsDetailsService() {
        return phone -> teachersRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("There is no Instructor with that phone number !!"));
    }

    @Bean
    public StaticText staticText() {
        return StaticText
                .builder()
                .aboutText("Your default About text")
                .privacyText("Your default Privacy text")
                .build();
    }
}
