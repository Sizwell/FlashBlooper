package com.itech.Blooper.words.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sensitive_words")
public class SensitiveWords {
    @Id
    @SequenceGenerator(
            name = "secrete_words",
            sequenceName = "secrete_words",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "secrete_words"
    )


    private Long id;
    @Column(name = "words")
    private String words;

    public SensitiveWords() {
    }

    public SensitiveWords(Long id, String words) {
        this.id = id;
        this.words = words;
    }

    public SensitiveWords(String words) {
        this.words = words;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "SensitiveWords{" +
                "id=" + id +
                ", words='" + words + '\'' +
                '}';
    }
}
