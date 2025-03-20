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
import rd.portfolio.portfolioserver.exception.ProjectAlreadyExistException;
import rd.portfolio.portfolioserver.exception.ProjectNotFoundException;
import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.ProjectParams;
import rd.portfolio.portfolioserver.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private SecurityUtil securityUtil;
    private ProjectService projectService;
    private Project project;
    private ProjectParams projectParams;

    public static Stream<Arguments> projectParamsProvider() {
        return Stream.of(
                Arguments.of(null, "https://example.com/image.png", "https://example.url.com"),
                Arguments.of("", "https://example.com/image.png", "https://example.url.com"),
                Arguments.of("name", null, "https://example.url.com"),
                Arguments.of("name", "", "https://example.url.com"),
                Arguments.of("name", "https://example.com/image.png", null),
                Arguments.of("name", "https://example.com/image.png", "")
        );
    }

    @BeforeEach
    void setUp() {
        projectService = new ProjectServiceImpl(securityUtil, projectRepository);
        project = new Project() {{
            setId(1L);
            setName("Test Project");
            setImage("https://example.com/image.png");
            setUrl("https://example.url.com");
            setDescription("Test Description");
        }};
        projectParams = new ProjectParams() {{
            setUserId(1L);
            setName("Test Project");
            setImage("https://example.com/image.png");
            setUrl("https://example.url.com");
            setDescription("Test Description");
        }};
    }

    @Test
    void projectExistsThrowsException() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(new User() {{
            setId(1L);
            setUsername("Test User");
        }});

        when(projectRepository.findByName("Test Project")).thenReturn(Optional.of(project));
        assertThrows(ProjectAlreadyExistException.class, () -> projectService.save(projectParams));

        verify(projectRepository, never()).save(any());

    }

    @ParameterizedTest
    @MethodSource("projectParamsProvider")
    void validateProjectParams(String name, String image, String url) {
        projectParams.setName(name);
        projectParams.setImage(image);
        projectParams.setUrl(url);

        assertThrows(InvalidParameterException.class, () -> projectService.save(projectParams));
        verify(projectRepository, never()).save(any());
    }

    @Test
    void saveProjectSuccess() {
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(new User() {{
            setId(1L);
            setUsername("test user");
        }});
        when(projectRepository.findByName("Test Project")).thenReturn(Optional.empty());
        when(projectRepository.save(any())).thenReturn(project);
        var result = projectService.save(projectParams);
        assertEquals(1L, result.getId());
        assertEquals("Test Project", result.getName());

    }

    @Test
    void findAll() {
        when(projectRepository.findAll()).thenReturn(List.of(project));
        var result = projectService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    void findById() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        var result = projectService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findByIdThrowsException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
      assertThrows(ProjectNotFoundException.class, () -> projectService.findById(1L));
    }

    @Test
    void delete() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        User user =new User() {{setId(1L);setUsername("test user");}};
       project.setUser(user);
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);

        projectService.delete(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProjectThrowException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.delete(1L));

        verify(projectRepository, never()).deleteById(1L);
    }

    @Test
    void update() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        User user =new User() {{setId(1L);setUsername("test user");}};
        when(securityUtil.ensureLoggedUser(1L)).thenReturn(user);
        projectParams.setName("updated name");
        when(projectRepository.save(any())).thenReturn(project);
        var result = projectService.update(1L, projectParams);
        assertEquals(1L, result.getId());
        assertEquals("updated name", result.getName());
    }

    @Test
    void existsById() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        assertTrue(projectService.existsById(1L));
    }

    @Test
    void existsByName() {
        when(projectRepository.existsById(1L)).thenReturn(false);
        assertFalse(projectService.existsById(1L));
    }
}