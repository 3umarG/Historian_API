package com.example.historian_api.services.base.posts;

import com.example.historian_api.dtos.responses.BookmarkResponseDto;
import com.example.historian_api.dtos.responses.PostResponseDto;

import java.util.List;

public interface BookmarksService {
    BookmarkResponseDto addOrDeletePostBookMarkForStudent(Integer postId, Integer studentId);

    List<PostResponseDto> getBookMarksForStudentId(Integer studentId);
}
