package rd.portfolio.portfolioserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rd.portfolio.portfolioserver.dto.SocialDTO;
import rd.portfolio.portfolioserver.model.Social;
import rd.portfolio.portfolioserver.params.SocialParams;
import rd.portfolio.portfolioserver.service.SocialService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialController {
    private final SocialService socialService;

    @PostMapping()
    public ResponseEntity<SocialDTO> addSocial(@RequestBody SocialParams socialParams) {
        Social social = this.socialService.createSocial(socialParams);
        return new ResponseEntity<>(social.convertToDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialDTO> getSocialById(@PathVariable Long id) {
        Social social = this.socialService.getSocialById(id);
        return new ResponseEntity<>(social.convertToDTO(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialDTO> updateSocial(@PathVariable Long id, @RequestBody SocialParams socialParams) {
        Social social = this.socialService.updateSocial(id, socialParams);
        return new ResponseEntity<>(social.convertToDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSocial(@PathVariable Long id) {
        this.socialService.deleteSocialById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
