package rd.portfolio.portfolioserver.params;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserParam {
    private String username;
    private String email;
    private LocalDate birthday;
    private String phoneNumber;
    private String imageUrl;
    private String aboutMe;
    private String profession;
    private String sex;
}
