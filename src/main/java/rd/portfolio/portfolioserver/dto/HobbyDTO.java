package rd.portfolio.portfolioserver.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.Hobby;
import rd.portfolio.portfolioserver.model.Summary;

@Data
@Getter
@Setter
public class HobbyDTO {
    private Long id;
    private String name;
    private String imageUrl;

    public Hobby convertToHobby() {
        Hobby hobby = new Hobby();
        hobby.setId(this.id);
        hobby.setName(this.name);
        hobby.setImageUrl(this.imageUrl);
        return hobby;
    }
}
