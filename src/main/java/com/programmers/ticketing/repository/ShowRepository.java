package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Page<Show> findAllByTitleContaining(String title, Pageable pageable);
}
