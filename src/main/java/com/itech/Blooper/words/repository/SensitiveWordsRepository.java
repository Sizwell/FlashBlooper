package com.itech.Blooper.words.repository;

import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SensitiveWordsRepository  extends JpaRepository<SensitiveWords, Long>
{
    //Optional<SensitiveWords> findSensitiveWords(@Param("words") String word);
    Optional<SensitiveWords> findSensitiveWordsByWords(@Param("words") String word);
}
