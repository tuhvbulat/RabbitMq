package ru.pcs.model;

import com.google.gson.Gson;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMap {
    private long id;
    private String firstName;
    private String lastName;

    private static Gson gson = new Gson();

    @Override
    public String toString(){
        return gson.toJson(this);
    }
}
