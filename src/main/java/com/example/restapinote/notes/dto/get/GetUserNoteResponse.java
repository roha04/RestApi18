package com.example.restapinote.notes.dto.get;

import com.example.restapinote.notes.Note;
import lombok.Builder;
import lombok.Data;

//import java.util.List;
@Builder
@Data
public class GetUserNoteResponse {
    private GetUserNoteResponse.Error error;
    private Note userNote;
    public enum Error {
        ok,
        invalidNoteId,
        insufficientPrivileges
    }
    public static GetUserNoteResponse success(Note userNote) {
        return builder().error(GetUserNoteResponse.Error.ok).userNote(userNote).build();
    }
    public static GetUserNoteResponse failed(GetUserNoteResponse.Error error) {
        return builder().error(error).userNote(null).build();
    }
}
