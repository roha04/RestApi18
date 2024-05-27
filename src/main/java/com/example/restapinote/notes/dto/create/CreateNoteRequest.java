package com.example.restapinote.notes.dto.create;

import lombok.*;
@Data
public class CreateNoteRequest {
    private String title;
    private String content;
}
