package rd.portfolio.portfolioserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rd.portfolio.portfolioserver.dto.SkillDTO;
import rd.portfolio.portfolioserver.exception.ExperienceAlreadyExistException;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.Summary;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.ExperienceParams;
import rd.portfolio.portfolioserver.repository.ExperienceRepository;
import rd.portfolio.portfolioserver.repository.SummaryRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceImplTest {
    @Mock
    private SecurityUtil securityUtil;
    @Mock
    private ExperienceRepository experienceRepository;
    @Mock
    private SummaryRepository summaryRepository;
    private ExperienceService experienceService;
    private ExperienceParams experienceParams;
    private Experience experience;

    public static Stream<Arguments> experienceParamsProvider() {
        return Stream.of(
                Arguments.of(null, "company", "http://image.test.com", "http://url.test.com", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("", "company", "http://image.test.com", "http://url.test.com", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", null, "http://image.test.com", "http://url.test.com", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "", "http://image.test.com", "http://url.test.com", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", null, "http://url.test.com", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", "", "http://url.test.com", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", "http://image.test.com", null, "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", "http://image.test.com", "", "2025-03-05T00:00:00+01:00", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", "http://image.test.com", "http://url.test.com", null, "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", "http://image.test.com", "http://url.test.com", "", "2025-03-18T00:00:00+01:00"),
                Arguments.of("name", "company", "http://image.test.com", "http://url.test.com", "2025-03-05T00:00:00+01:00", null),
                Arguments.of("name", "company", "http://image.test.com", "http://url.test.com", "2025-03-05T00:00:00+01:00", "")
        );
    }

    @BeforeEach
    void setUp() {
        experienceService = new ExperienceServiceImpl(securityUtil, experienceRepository, summaryRepository);
        experienceParams = new ExperienceParams() {{
            setUserId(1L);
            setName("name");
            setCompany("company");
            setImage("http://image.test.com");
            setUrl("http://url.test.com");
            setDescription("this is a description");
            setStartedAt("2025-03-05T00:00:00+01:00");
            setEndedAt("2025-03-18T00:00:00+01:00");
            setTechnologies(new ArrayList<>());
            setSummaries(new ArrayList<>());
        }};
        experience = new Experience() {{
            setId(1L);
            setName("name");
            setCompany("company");
            setImage("http://image.test.com");
            setUrl("http://url.test.com");
            setDescription("this is a description");
            setStartedAt(Timestamp.from(Instant.parse("2025-03-05T00:00:00+01:00")));
            setEndedAt(Timestamp.from(Instant.parse("2025-03-18T00:00:00+01:00")));
            setTechnologies(new ArrayList<>());
            setSummaries(new ArrayList<>());
            setUser(new User(){{
                setId(1L);
                setName("username");
            }});
        }};

    }

    @Test
    void experienceAlreadyExistThrowException() {

        when(securityUtil.ensureLoggedUser(1L)).thenReturn(new User() {{
            setId(1L);
            setUsername("name");
        }});
        when(experienceRepository.findByName("name")).thenReturn(Optional.of(this.experience));

        assertThrows(ExperienceAlreadyExistException.class, () -> experienceService.save(experienceParams));

        verify(experienceRepository, never()).save(any());
    }

    @ParameterizedTest
    @MethodSource("experienceParamsProvider")
    void validateExperienceParams(String name, String Company, String Image, String Url, String StartedAt, String EndedAt) {
        experienceParams.setName(name);
        experienceParams.setCompany(Company);
        experienceParams.setImage(Image);
        experienceParams.setUrl(Url);
        experienceParams.setStartedAt(StartedAt);
        experienceParams.setEndedAt(EndedAt);

        assertThrows(InvalidParameterException.class, () -> experienceService.save(experienceParams));

    }

    @Test
    void save() {
        this.experienceParams.getTechnologies().add(new SkillDTO() {{
            setId(1L);
        }});
        this.experienceParams.getSummaries().add(new Summary() {{
            setId(1L);
        }});
        this.experience.getTechnologies().add(new Skill() {{
            setId(1L);
        }});
        this.experience.getSummaries().add(new Summary() {{
            setId(1L);
        }});
        when(experienceRepository.save(any())).thenReturn(experience);
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(new User() {{
            setId(1L);
            setUsername("name");
        }});
        when(experienceRepository.findByName("name")).thenReturn(Optional.empty());

        var result = experienceService.save(experienceParams);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1, result.getTechnologies().size());
        assertEquals(1, result.getSummaries().size());

        verify(experienceRepository, times(1)).save(any());
        verify(summaryRepository, times(1)).save(any());

    }

    @Test
    void findAll() {
        when(experienceRepository.findAll()).thenReturn(List.of(experience, new Experience()));
        var result = experienceService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));
        var result = experienceService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void delete() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(new User() {{setId(1L);setUsername("username");}});
        experienceService.delete(1L);

        verify(experienceRepository, times(1)).delete(experience);
    }

    @Test
    void update() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(new User() {{setId(1L);setUsername("username");}});
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));
        experienceParams.setName("newName");
        when(experienceRepository.save(any())).thenReturn(experience);

        var result = experienceService.update(1L, experienceParams);
        assertEquals("newName", result.getName());
    }

    @Test
    void existsById() {
        when(experienceRepository.existsById(1L)).thenReturn(true);
        var result = experienceService.existsById(1L);
        assertTrue(result);
    }

    @Test
    void existsByName() {
        when(experienceRepository.findByName("name")).thenReturn(Optional.empty());
        var result = experienceService.existsByName("name");
        assertFalse(result);
    }
}