package com.liferay.hhportlet.model;

public class Vacancy {
    private String name;
    private String employerName;
    private String publishedAt;
    private Double salaryFrom;
    private Double salaryTo;
    private String currency;

    public Vacancy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Double getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Double salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Double getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Double salaryTo) {
        this.salaryTo = salaryTo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return vacancy.name.equals(name) && vacancy.employerName.equals(employerName)
                && vacancy.publishedAt.equals(publishedAt) && vacancy.salaryTo.equals(salaryTo)
                && vacancy.salaryFrom.equals(salaryFrom) && vacancy.currency.equals(currency);
    }

    @Override
    public String toString() {
        StringBuilder values = new StringBuilder("('");
        values.append(name)
                .append("','")
                .append(employerName)
                .append("','")
                .append(publishedAt)
                .append("',")
                .append(salaryFrom)
                .append(",")
                .append(salaryTo)
                .append(",'")
                .append(currency)
                .append("')");
        return values.toString();
    }
}
