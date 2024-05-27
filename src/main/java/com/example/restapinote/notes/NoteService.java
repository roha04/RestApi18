package com.example.restapinote.notes;

import com.example.restapinote.notes.dto.create.CreateNoteRequest;
import com.example.restapinote.notes.dto.create.CreateNoteResponse;
import com.example.restapinote.notes.dto.delete.DeleteNoteResponse;
import com.example.restapinote.notes.dto.get.GetUserNoteResponse;
import com.example.restapinote.notes.dto.get.GetUserNotesResponse;
import com.example.restapinote.notes.dto.update.UpdateNoteRequest;
import com.example.restapinote.notes.dto.update.UpdateNoteResponse;
import com.example.restapinote.users.User;
import com.example.restapinote.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_CONTENT_LENGTH = 1000;

    private final NoteRepository repository;
    private final UserService userService;

    public CreateNoteResponse createNoteResponse(String username, CreateNoteRequest request) {
        Optional<CreateNoteResponse.Error> validationError = validateCreateFields(request);
        if (validationError.isPresent()) {
            return CreateNoteResponse.failed(validationError.get());
        }
        User user = userService.findByUsername(username);
        Note createdNote = repository.save(Note.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build());
        return CreateNoteResponse.success(createdNote.getId());
    }
    public GetUserNotesResponse getUserNotesResponse(String username) {
        List<Note> userNotes = repository.getUserNotes(username);
        return GetUserNotesResponse.success(userNotes);
    }
    public GetUserNoteResponse getUserNoteResponse(String username, long id) {
        Optional<Note> optionalNote = repository.findById(id);
        if (optionalNote.isEmpty()) {
            return GetUserNoteResponse.failed(GetUserNoteResponse.Error.invalidNoteId);
        }
        Note note = optionalNote.get();
        boolean isNotUserNote = isNotUserNote(username, note);
        if (isNotUserNote) {
            return GetUserNoteResponse.failed(GetUserNoteResponse.Error.insufficientPrivileges);
        }
        return GetUserNoteResponse.success(note);
    }
    public UpdateNoteResponse updateNoteResponse(String username, UpdateNoteRequest request) {
        Optional<Note> optionalNote = repository.findById(request.getId());
        if (optionalNote.isEmpty()) {
            return UpdateNoteResponse.failed(UpdateNoteResponse.Error.invalidNoteId);
        }
        Note note = optionalNote.get();
        boolean isNotUserNote = isNotUserNote(username, note);
        if (isNotUserNote) {
            return UpdateNoteResponse.failed(UpdateNoteResponse.Error.insufficientPrivileges);
        }
        Optional<UpdateNoteResponse.Error> validationError = validateUpdateFields(request);
        if (validationError.isPresent()) {
            return UpdateNoteResponse.failed(validationError.get());
        }
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        repository.save(note);
        return UpdateNoteResponse.success(note);
    }

    private boolean isNotUserNote(String username, Note note) {
        return !note.getUser().getUserId().equals(username);
    }
    public DeleteNoteResponse deleteNoteResponse(String username, long id) {
        Optional<Note> optionalNote = repository.findById(id);
        if (optionalNote.isEmpty()) {
            return DeleteNoteResponse.failed(DeleteNoteResponse.Error.invalidNoteId);
        }
        Note note = optionalNote.get();
        boolean isNotUserNote = isNotUserNote(username, note);
        if (isNotUserNote) {
            return DeleteNoteResponse.failed(DeleteNoteResponse.Error.insufficientPrivileges);
        }
        repository.delete(note);
        return DeleteNoteResponse.success();
    }
    private Optional<CreateNoteResponse.Error> validateCreateFields(CreateNoteRequest request) {
        if (Objects.isNull(request.getTitle()) || request.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(CreateNoteResponse.Error.invalidTitle);
        }
        if (Objects.isNull(request.getContent()) || request.getContent().length() > MAX_CONTENT_LENGTH) {
            return Optional.of(CreateNoteResponse.Error.invalidContent);
        }
        return Optional.empty();
    }
    private Optional<UpdateNoteResponse.Error> validateUpdateFields(UpdateNoteRequest request) {
        if (Objects.nonNull(request.getTitle()) && request.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(UpdateNoteResponse.Error.invalidTitleLength);
        }
        if (Objects.nonNull(request.getContent()) && request.getContent().length() > MAX_CONTENT_LENGTH) {
            return Optional.of(UpdateNoteResponse.Error.invalidContentLength);
        }
        return Optional.empty();
    }

}
