package com.itech.Blooper.words.repository;

import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveWordsRepository  extends JpaRepository<SensitiveWords, Long>
{

}
