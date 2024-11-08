package rd.portfolio.portfolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rd.portfolio.portfolioserver.model.Social;

@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {

    Boolean existsByName(String name);
}
