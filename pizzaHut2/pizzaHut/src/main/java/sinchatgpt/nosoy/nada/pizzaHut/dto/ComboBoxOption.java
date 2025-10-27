package sinchatgpt.nosoy.nada.pizzaHut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComboBoxOption {

    String key;

    String value;

    @Override

    public String toString (){

        return value;

    }

}



