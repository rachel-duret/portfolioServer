package rd.portfolio.portfolioserver.params;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.HobbyDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UpdateProfileParams {
    private String birthday;
    private String phoneNumber;
    private String imageUrl;
    private String aboutMe;
    private String profession;
    private String sex;
    private List<HobbyDTO> hobbies;
}
