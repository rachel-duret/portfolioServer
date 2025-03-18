package rd.portfolio.portfolioserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rd.portfolio.portfolioserver.exception.HobbyNotFoundException;
import rd.portfolio.portfolioserver.model.Hobby;
import rd.portfolio.portfolioserver.repository.HobbyRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HobbyServiceImplTest {
    @Mock
    HobbyRepository hobbyRepository;
    HobbyServiceImpl hobbyService;

    @BeforeEach
    void setUp() {
        hobbyService = new HobbyServiceImpl(hobbyRepository);
    }

    @Test
    void deleteByIdThrowException() {
        when(hobbyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(HobbyNotFoundException.class, () -> hobbyService.deleteById(1L));

        verify(hobbyRepository, never()).deleteById(1L);
    }

    @Test
    void deleteByIdSuccess() {
        when(hobbyRepository.findById(1L)).thenReturn(Optional.of(new Hobby() {{
            setId(1L);
        }}));
        hobbyService.deleteById(1L);

        verify(hobbyRepository, times(1)).deleteById(1L);
    }
}