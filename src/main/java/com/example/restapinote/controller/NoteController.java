package com.example.restapinote.controller;

import com.example.restapinote.notes.NoteService;
import com.example.restapinote.notes.dto.create.CreateNoteRequest;
import com.example.restapinote.notes.dto.create.CreateNoteResponse;
import com.example.restapinote.notes.dto.delete.DeleteNoteResponse;
import com.example.restapinote.notes.dto.get.GetUserNoteResponse;
import com.example.restapinote.notes.dto.get.GetUserNotesResponse;
import com.example.restapinote.notes.dto.update.UpdateNoteRequest;
import com.example.restapinote.notes.dto.update.UpdateNoteResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public CreateNoteResponse create(Principal principal, @RequestBody CreateNoteRequest request) {
        return noteService.createNoteResponse(principal.getName(), request);
    }
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public GetUserNotesResponse getUserNotes(Principal principal) {
        return noteService.getUserNotesResponse(principal.getName());
    }
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public GetUserNoteResponse getUserNote(Principal principal, @PathVariable("id") long id) {
        return noteService.getUserNoteResponse(principal.getName(), id);
    }
    @PatchMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public UpdateNoteResponse update(Principal principal, @RequestBody UpdateNoteRequest request) {
        return noteService.updateNoteResponse(principal.getName(), request);
    }
    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public DeleteNoteResponse delete(Principal principal, @RequestParam(name = "id") long id) {
        return noteService.deleteNoteResponse(principal.getName(), id);
    }
}
