package pe.edu.upeu.sysventas.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
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



