package respose;

import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AnimalCard(String name,

                         LocalDate birthDate,

                         BigDecimal weight,

                         boolean sterilization,

                         Sex sex,

                         Type type,

                         Image image) {


}
