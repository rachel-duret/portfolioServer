package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import rd.portfolio.portfolioserver.model.Profile;

import java.time.LocalDate;

@Data
public class ProfileDTO {
    private Long id;

    private String phone;
    private RoleType role;
    private String imageUrl;
    private String aboutMe;
    private String profession;
    private String sex;
    private LocalDate birthday;

    public Profile convertToProfile() {
        Profile profile = new Profile();
        profile.setPhone(this.getPhone());
        profile.setRole(this.getRole().name());
        profile.setImageUrl(this.getImageUrl());
        profile.setAboutMe(this.getAboutMe());
        profile.setProfession(this.getProfession());
        profile.setSex(this.getSex());
        profile.setBirthday(this.birthday);
        return profile;
    }
}
