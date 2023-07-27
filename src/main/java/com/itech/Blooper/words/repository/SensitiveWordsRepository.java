package com.itech.Blooper.words.repository;

import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SensitiveWordsRepository  extends JpaRepository<SensitiveWords, Long>
{
    Optional<SensitiveWords> findSensitiveWordsByWords(String word);

    @Query("SELECT sw FROM SensitiveWords sw WHERE sw.words = :words")
    List<SensitiveWords> findByWords(@Param("words") String word);

}
