package rd.portfolio.portfolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rd.portfolio.portfolioserver.model.Hobby;

import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

//    Optional<Hobby> findById(Long id);
}
