package com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StudyExperienceExample extends MyBatisExample {

    public CriteriaExt createCriteriaExt() {
        CriteriaExt criteria = new CriteriaExt();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public static class CriteriaExt extends Criteria {

        protected CriteriaExt() {
            super(StudyExperience.class);
        }

        public CriteriaExt andIdIsNull() {
            addCriterion("id is null");
            return  this;
        }

        public CriteriaExt andIdIsNotNull() {
            addCriterion("id is not null");
            return  this;
        }

        public CriteriaExt andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return  this;
        }

        public CriteriaExt andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return  this;
        }

        public CriteriaExt andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return  this;
        }

        public CriteriaExt andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return  this;
        }

        public CriteriaExt andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return  this;
        }

        public CriteriaExt andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return  this;
        }

        public CriteriaExt andIdLike(String value) {
            addCriterion("id like", value, "id");
            return  this;
        }

        public CriteriaExt andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return  this;
        }

        public CriteriaExt andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return  this;
        }

        public CriteriaExt andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return  this;
        }

        public CriteriaExt andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return  this;
        }

        public CriteriaExt andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return  this;
        }

        public CriteriaExt andSchoolIdIsNull() {
            addCriterion("school_id is null");
            return  this;
        }

        public CriteriaExt andSchoolIdIsNotNull() {
            addCriterion("school_id is not null");
            return  this;
        }

        public CriteriaExt andSchoolIdEqualTo(Integer value) {
            addCriterion("school_id =", value, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdNotEqualTo(Integer value) {
            addCriterion("school_id <>", value, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdGreaterThan(Integer value) {
            addCriterion("school_id >", value, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("school_id >=", value, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdLessThan(Integer value) {
            addCriterion("school_id <", value, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("school_id <=", value, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdIn(List<Integer> values) {
            addCriterion("school_id in", values, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdNotIn(List<Integer> values) {
            addCriterion("school_id not in", values, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdBetween(Integer value1, Integer value2) {
            addCriterion("school_id between", value1, value2, "schoolId");
            return  this;
        }

        public CriteriaExt andSchoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("school_id not between", value1, value2, "schoolId");
            return  this;
        }

        public CriteriaExt andStartDateIsNull() {
            addCriterion("start_date is null");
            return  this;
        }

        public CriteriaExt andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return  this;
        }

        public CriteriaExt andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return  this;
        }

        public CriteriaExt andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return  this;
        }

        public CriteriaExt andEndDateIsNull() {
            addCriterion("end_date is null");
            return  this;
        }

        public CriteriaExt andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return  this;
        }

        public CriteriaExt andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return  this;
        }

        public CriteriaExt andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return  this;
        }

        public CriteriaExt andPersonIdIsNull() {
            addCriterion("person_id is null");
            return  this;
        }

        public CriteriaExt andPersonIdIsNotNull() {
            addCriterion("person_id is not null");
            return  this;
        }

        public CriteriaExt andPersonIdEqualTo(String value) {
            addCriterion("person_id =", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdNotEqualTo(String value) {
            addCriterion("person_id <>", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdGreaterThan(String value) {
            addCriterion("person_id >", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdGreaterThanOrEqualTo(String value) {
            addCriterion("person_id >=", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdLessThan(String value) {
            addCriterion("person_id <", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdLessThanOrEqualTo(String value) {
            addCriterion("person_id <=", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdLike(String value) {
            addCriterion("person_id like", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdNotLike(String value) {
            addCriterion("person_id not like", value, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdIn(List<String> values) {
            addCriterion("person_id in", values, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdNotIn(List<String> values) {
            addCriterion("person_id not in", values, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdBetween(String value1, String value2) {
            addCriterion("person_id between", value1, value2, "personId");
            return  this;
        }

        public CriteriaExt andPersonIdNotBetween(String value1, String value2) {
            addCriterion("person_id not between", value1, value2, "personId");
            return  this;
        }
    }
}