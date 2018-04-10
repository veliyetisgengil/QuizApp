package com.veliyetisgengil.onlinequizapp.Common;

import com.veliyetisgengil.onlinequizapp.Model.Question;
import com.veliyetisgengil.onlinequizapp.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by veliyetisgengil on 29.03.2018.
 */

public class Common {

    public static String categoryId;
    public static String categoryName;
    public static User currentUser;
    public static List<Question> questionList = new ArrayList<>();
    public static final String STR_PUSH = "pushNotification";
}
