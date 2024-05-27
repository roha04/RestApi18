package com.example.restapinote.notes;

import com.example.restapinote.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    /** @JsonIgnore - Це корисно, коли ви не хочете відображати певну інформацію у відповіді API
     *  Без даної анотації Jackson викине exception, і треба буде замість `List<Note>`
     *  використовувати DTO на кшталт `List<NoteDTO>` без поля `User user`.
     *  Стається це тому що `User` містить посилання на Note, і навпаки,
     *  що створює "циклічну залежність" та проблеми при серіалізації.
     */
    private User user;
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();
}
