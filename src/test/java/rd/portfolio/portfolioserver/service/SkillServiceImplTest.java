package rd.portfolio.portfolioserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.SkillAlreadyExistException;
import rd.portfolio.portfolioserver.exception.SkillNotFoundException;
import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.SkillParams;
import rd.portfolio.portfolioserver.repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {
    @Mock
    private SkillRepository skillRepository;
    @Mock
    private SecurityUtil securityUtil;
    private SkillServiceImpl skillService;
    private User user;
    private SkillParams skillParams;
    private Skill skill;

    public static Stream<Arguments> validateSkillParamsProvider() {
        return Stream.of(
                Arguments.of(null, "https//image.com", "https//url.com"),
                Arguments.of("", "https//image.com", "https//url.com"),
                Arguments.of("test", null, "https//url.com"),
                Arguments.of("test", "", "https//url.com"),
                Arguments.of("test", "https//image.com", null),
                Arguments.of("test", "https//image.com", "")
        );
    }

    @BeforeEach
    void setUp() {
        skillService = new SkillServiceImpl(skillRepository, securityUtil);
        skillParams = new SkillParams() {{
            setUserId(1L);
            setName("test");
            setImage("https//image.com");
            setUrl("https//url.com");
        }};
        user = new User() {{
            setId(1L);
            setUsername("username");
        }};
        skill = new Skill() {{
            setId(1L);
            setName("test");
            setImage("https//image.com");
            setUrl("https//url.com");
        }};

    }

    @ParameterizedTest
    @MethodSource("validateSkillParamsProvider")
    void validateSkillParams(String name, String image, String url) {
        skillParams.setName(name);
        skillParams.setImage(image);
        skillParams.setUrl(url);

        assertThrows(InvalidParameterException.class, () -> skillService.save(skillParams));

        verify(skillRepository, never()).save(any());
    }

    @Test
    void skillAlreadyExistThrowException() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);
        when(skillRepository.findByName("test")).thenReturn(Optional.of(skill));
        assertThrows(SkillAlreadyExistException.class, () -> skillService.save(skillParams));

        verify(skillRepository, never()).save(any());
    }

    @Test
    void save() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);
        when(skillRepository.findByName("test")).thenReturn(Optional.empty());
        when(skillRepository.save(any())).thenReturn(skill);
        var result = skillService.save(skillParams);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());

    }

    @Test
    void findAll() {
        when(skillRepository.findAll()).thenReturn(List.of(skill));
        var result = skillService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByIdThrowException() {
        when(skillRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(SkillNotFoundException.class, () -> skillService.findById(1L));
    }

    @Test
    void findById() {
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        var result = skillService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void delete() {
        Experience experience1 = new Experience() {{
            setId(1L);
            setTechnologies(new ArrayList<>(){{
                add(skill);
            }});
        }};
        Experience experience2 =new Experience() {{
            setId(2L);
            setTechnologies(new ArrayList<>(){{
                add(skill);
                            }}

            );
        }};
        skill.setUser(user);
        skill.setExperiences(List.of(experience1, experience2));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);
        skillService.delete(1L);
        verify(skillRepository, times(1)).deleteById(1L);
        assertEquals(0, experience1.getTechnologies().size());
        assertEquals(0, experience2.getTechnologies().size());
    }

    @Test
    void update() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(skillRepository.save(any())).thenReturn(skill);
        skillParams.setName("update");
        var result = skillService.update(1L, skillParams);
        assertNotNull(result);
        assertEquals("update", result.getName());
    }

    @Test
    void updateSkillNotExist() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);
        when(skillRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(SkillNotFoundException.class, () -> skillService.update(1L, skillParams));
        verify(skillRepository, never()).save(any());
    }

    @Test
    void existsById() {
        when(skillRepository.existsById(1L)).thenReturn(true);
        assertTrue(skillService.existsById(1L));
    }

    @Test
    void existsByName() {
        when(skillRepository.findByName("test")).thenReturn(Optional.of(skill));
        assertTrue(skillService.existsByName("test"));
    }
}