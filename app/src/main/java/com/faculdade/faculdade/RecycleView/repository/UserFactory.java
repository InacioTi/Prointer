package com.faculdade.faculdade.RecycleView.repository;


import com.faculdade.faculdade.RecycleView.model.UserModel;

import java.util.Random;



public class UserFactory {

    private static String[] names = {"PROINTER IV", "Desafio Profissional IV", "Projeto IV", "Tarefa Redacao IV", "Projeto Android IV", "Tarefa Monografia "};
    private static String[] cities = {"Jose", "Inacio", "Pedro", "Joao", "Maurilio", "Cristiano"};
    private static String[] desc = {"\"projeto interdisciplinar aplicado aos cursos superiores de tecnologia.\n " +
            "Desafio Profissional.\"",
            "\"Tarefas\""};

    public static UserModel makeUser() {
        return new UserModel(names[getRandomValue(0, 5)],
                cities[getRandomValue(0, 5)],
                desc[getRandomValue(0, 2)],
                getRandomValue(18, 40));
    }

    private static int getRandomValue(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }
}
