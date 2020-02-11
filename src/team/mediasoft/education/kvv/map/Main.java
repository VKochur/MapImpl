package team.mediasoft.education.kvv.map;

import team.mediasoft.education.kvv.list.TwoDirectionList;

public class Main {

    public static void main(String[] args) {

        TwoDirectionList<String> directionList = new TwoDirectionList<>();
        String[] strings = {"sdfsf", "jkhkasf", "jkhwer893r"};
        for (String string : strings) {
            directionList.addLast(string);
        }

        System.out.println("you are here");
    }
}
