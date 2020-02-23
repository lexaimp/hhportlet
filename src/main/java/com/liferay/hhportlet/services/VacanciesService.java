package com.liferay.hhportlet.services;

import com.liferay.hhportlet.bd.JDBC;
import com.liferay.hhportlet.model.Vacancy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VacanciesService {

    public List<Vacancy> getVacanciesFromHH() {
        Map<String, Integer> queryArguments = new HashMap<>();
        queryArguments.put("specialization", 1);
        queryArguments.put("area", 4);
        queryArguments.put("per_page", 100);
        queryArguments.put("page", 1);

        String json = HTTPClient.getContentFromRequest("https://api.hh.ru/vacancies", queryArguments);

        List<Vacancy> vacancies = new ArrayList<>();
        try {
            JSONObject jsonObject = getJsonFromString(json);
            JSONArray jsonArray = (JSONArray) jsonObject.get("items");
            for (Object o : jsonArray) {
                JSONObject item = (JSONObject) o;
                Vacancy vacancy = new Vacancy();
                vacancy.setName(getValueByKey(item, "name"));

                JSONObject employer = getJsonFromString(getValueByKey(item, "employer"));
                vacancy.setEmployerName(getValueByKey(employer, "name"));

                vacancy.setPublishedAt(getValueByKey(item, "published_at"));

                if (getValueByKey(item, "salary") == null) {
                    vacancy.setSalaryFrom(null);
                    vacancy.setSalaryTo(null);
                    vacancy.setCurrency(null);
                    vacancies.add(vacancy);
                    continue;
                }

                JSONObject salary = getJsonFromString(getValueByKey(item, "salary"));
                String from = getValueByKey(salary, "from");
                vacancy.setSalaryFrom(from != null ? Double.parseDouble(from) : null);
                String to = getValueByKey(salary, "to");
                vacancy.setSalaryTo(to != null ? Double.parseDouble(to) : null);
                vacancy.setCurrency(getValueByKey(salary, "currency"));

                vacancies.add(vacancy);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return vacancies;
    }

    private static JSONObject getJsonFromString(String string) throws ParseException {
        if (string == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return (JSONObject) new JSONParser().parse(string);
    }

    private static String getValueByKey(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null) {
            return null;
        }
        return jsonObject.get(key) == null ? null : jsonObject.get(key).toString();
    }

    public void setVacanciesToBD(List<Vacancy> vacancies) {
        StringBuilder values = new StringBuilder();
        vacancies.forEach(vacancy -> values.append(vacancy).append(","));
        values.deleteCharAt(values.length() - 1);

        System.out.println(values);
        try (Statement statement = JDBC.getStatement()) {
            statement.executeUpdate("INSERT INTO Vacancies " +
                    "(name, employer_name, published_at, salary_from, salary_to, currency) " +
                    "VALUES " + values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setVacanciesToBD2(List<Vacancy> vacancies) {
        String insertString = "INSERT INTO Vacancies" +
                "(name, employer_name, published_at, salary_from, salary_to, currency) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = JDBC.getPreparedStatement(insertString)) {
            for (Vacancy vacancy : vacancies) {
                preparedStatement.setString(1, vacancy.getName());
                preparedStatement.setString(2, vacancy.getEmployerName());
                preparedStatement.setString(3, vacancy.getPublishedAt());
                if (vacancy.getSalaryFrom() == null) {
                    preparedStatement.setNull(4, Types.DOUBLE);
                } else {
                    preparedStatement.setDouble(4, vacancy.getSalaryFrom());
                }
                if (vacancy.getSalaryTo() == null) {
                    preparedStatement.setNull(5, Types.DOUBLE);
                } else {
                    preparedStatement.setDouble(5, vacancy.getSalaryTo());
                }
                if (vacancy.getSalaryTo() == null) {
                    preparedStatement.setNull(6, Types.VARCHAR);
                } else {
                    preparedStatement.setString(6, vacancy.getCurrency());
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Vacancy> getVacanciesFromBD() {
        List<Vacancy> vacancies = new ArrayList<>();
        try (Statement statement = JDBC.getStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Vacancies");
            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy();
                vacancy.setName(resultSet.getString("name"));
                vacancy.setEmployerName(resultSet.getString("employerName"));
                vacancy.setPublishedAt(resultSet.getString("publishedAt"));
                vacancies.add(vacancy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacancies;
    }
}
