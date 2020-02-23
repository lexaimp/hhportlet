package com.liferay.hhportlet;

import com.liferay.hhportlet.model.Vacancy;
import com.liferay.hhportlet.services.VacanciesService;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Application {
    public static void main(String[] args) throws ParseException {
        VacanciesService vacanciesService = new VacanciesService();
        List<Vacancy> vacancies = vacanciesService.getVacanciesFromHH();
        vacanciesService.setVacanciesToBD2(vacancies);
    }
}
