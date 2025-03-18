package rd.portfolio.portfolioserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rd.portfolio.portfolioserver.dto.ProjectDTO;
import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.params.ProjectParams;
import rd.portfolio.portfolioserver.service.HobbyService;
import rd.portfolio.portfolioserver.service.ProjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hobbies")
public class HobbieController {
    private final HobbyService hobbyService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneHobby(@PathVariable Long id) {
        hobbyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
