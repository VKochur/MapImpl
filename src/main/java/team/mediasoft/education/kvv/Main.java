package team.mediasoft.education.kvv;

import team.mediasoft.education.kvv.list.TwoDirectionList;

public class Main {

    public static void main(String[] args) {

        TwoDirectionList<String> directionList = new TwoDirectionList<>();
        String[] strings = {"sdfsf", "jkhkasf", "jkhwer893r"};
        for (String string : strings) {
            directionList.addToLastPlace(string);
        }

        System.out.println("you are here");
    }
}
