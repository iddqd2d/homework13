package com.grud;

public class Application {
    public static void main(String[] args) {
        System.out.println(new CustomSelection().getSumSalaryDevelopersInProject("zzz"));
        System.out.println(new CustomSelection().getDevelopersInProject("zzz"));
        System.out.println(new CustomSelection().getDevelopersByLanguage("Java"));
        System.out.println(new CustomSelection().getDevelopersByLevel("Middle"));
        System.out.println(new CustomSelection().getProjects());
    }
}
