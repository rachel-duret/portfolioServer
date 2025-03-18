package rd.portfolio.portfolioserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.exception.HobbyNotFoundException;
import rd.portfolio.portfolioserver.repository.HobbyRepository;

@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {
    private final HobbyRepository hobbyRepository;


    @Override
    public void deleteById(Long id) {
        this.hobbyRepository.findById(id).orElseThrow(HobbyNotFoundException::new);
        this.hobbyRepository.deleteById(id);
    }
}
