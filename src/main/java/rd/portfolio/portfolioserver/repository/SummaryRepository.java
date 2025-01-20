package rd.portfolio.portfolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.Summary;

import java.util.Optional;

public interface SummaryRepository extends JpaRepository<Summary, Long> {

}
