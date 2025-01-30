package rd.portfolio.portfolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rd.portfolio.portfolioserver.model.Hobby;
import rd.portfolio.portfolioserver.model.Summary;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

}
