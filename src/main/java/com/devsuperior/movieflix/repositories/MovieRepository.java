package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
                    SELECT  mov.*
                    FROM tb_movie as mov
                    INNER JOIN tb_genre as gen ON mov.genre_id = gen.id
                    WHERE (:genreId IS NULL OR  gen.id = :genreId)
                    ORDER BY mov.title ASC
            """,
            countQuery = """
                        SELECT COUNT(*) FROM (
                            SELECT  mov.*
                            FROM tb_movie as mov
                            INNER JOIN tb_genre as gen ON mov.genre_id = gen.id
                            WHERE (:genreId IS NULL OR  gen.id = :genreId)
                            ORDER BY mov.title ASC
                    	) AS tb_result
                    """)
    Page<Movie> searchMovies(Long genreId, Pageable pageable);
}
