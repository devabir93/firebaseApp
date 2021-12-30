package com.ucas.android.firebaseapp.utils;

import android.content.Context;

import com.ucas.android.firebaseapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<User> getUsers(Context context) {
        List<User> users = new ArrayList<>();
        users.add(new User("Lamia", "lamia@gmail.com", 989898, 12));
        users.add(new User("Rawan", "rawan@gmail.com", 454545, 22));
        users.add(new User("Aya", "aya@gmail.com", 232323, 30));
        users.add(new User("Mariam", "mariam@gmail.com", 876543, 35));
        users.add(new User("Jenin", "jenin@gmail.com"));
        users.add(new User("Razan", "jenin@gmail.com"));
        return users;
    }
}
