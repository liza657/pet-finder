package respose;

import com.example.petfinder.dto.user.response.UserCard;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Size;
import com.example.petfinder.model.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AnimalView(String name,

                         LocalDate birthDate,

                         BigDecimal weight,

                         String description,

                         boolean sterilization,

                         Sex sex,

                         Size size,

                         Type type,

                         byte[] image,

                         UserCard owner) {


}
