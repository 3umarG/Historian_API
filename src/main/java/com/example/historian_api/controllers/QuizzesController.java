package com.example.historian_api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}/quizzes")
@Tag(name = "Quizzes")
public class QuizzesController {
}
