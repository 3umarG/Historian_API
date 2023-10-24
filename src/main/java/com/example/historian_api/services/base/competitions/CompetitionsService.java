package com.example.historian_api.services.base.competitions;

import com.example.historian_api.dtos.requests.CompetitionRequestDto;
import com.example.historian_api.dtos.responses.CompetitionResponseDto;

import java.io.IOException;
import java.util.List;

public interface CompetitionsService {

    List<CompetitionResponseDto> getAll();

    List<CompetitionResponseDto> getTop(Integer count);

    CompetitionResponseDto addCompetition(Integer teacherId , CompetitionRequestDto requestDto) throws IOException;
}
