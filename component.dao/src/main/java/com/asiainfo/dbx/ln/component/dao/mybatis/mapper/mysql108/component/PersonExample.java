package com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PersonExample extends MyBatisExample {

    public CriteriaExt createCriteriaExt() {
        CriteriaExt criteria = new CriteriaExt();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public void andPrimaryKeyEqualTo(String primaryKey) {
         this.createCriteria().addCriterion("id =", primaryKey, "id");
    }

    public static class CriteriaExt extends Criteria {

        protected CriteriaExt() {
            super(Person.class);
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

        public CriteriaExt andNameIsNull() {
            addCriterion("name is null");
            return  this;
        }

        public CriteriaExt andNameIsNotNull() {
            addCriterion("name is not null");
            return  this;
        }

        public CriteriaExt andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return  this;
        }

        public CriteriaExt andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return  this;
        }

        public CriteriaExt andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return  this;
        }

        public CriteriaExt andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return  this;
        }

        public CriteriaExt andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return  this;
        }

        public CriteriaExt andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return  this;
        }

        public CriteriaExt andNameLike(String value) {
            addCriterion("name like", value, "name");
            return  this;
        }

        public CriteriaExt andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return  this;
        }

        public CriteriaExt andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return  this;
        }

        public CriteriaExt andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return  this;
        }

        public CriteriaExt andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return  this;
        }

        public CriteriaExt andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return  this;
        }

        public CriteriaExt andIdentityNumberIsNull() {
            addCriterion("identity_number is null");
            return  this;
        }

        public CriteriaExt andIdentityNumberIsNotNull() {
            addCriterion("identity_number is not null");
            return  this;
        }

        public CriteriaExt andIdentityNumberEqualTo(Integer value) {
            addCriterion("identity_number =", value, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberNotEqualTo(Integer value) {
            addCriterion("identity_number <>", value, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberGreaterThan(Integer value) {
            addCriterion("identity_number >", value, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("identity_number >=", value, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberLessThan(Integer value) {
            addCriterion("identity_number <", value, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberLessThanOrEqualTo(Integer value) {
            addCriterion("identity_number <=", value, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberIn(List<Integer> values) {
            addCriterion("identity_number in", values, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberNotIn(List<Integer> values) {
            addCriterion("identity_number not in", values, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberBetween(Integer value1, Integer value2) {
            addCriterion("identity_number between", value1, value2, "identityNumber");
            return  this;
        }

        public CriteriaExt andIdentityNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("identity_number not between", value1, value2, "identityNumber");
            return  this;
        }

        public CriteriaExt andSexIsNull() {
            addCriterion("sex is null");
            return  this;
        }

        public CriteriaExt andSexIsNotNull() {
            addCriterion("sex is not null");
            return  this;
        }

        public CriteriaExt andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return  this;
        }

        public CriteriaExt andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return  this;
        }

        public CriteriaExt andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return  this;
        }

        public CriteriaExt andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return  this;
        }

        public CriteriaExt andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return  this;
        }

        public CriteriaExt andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return  this;
        }

        public CriteriaExt andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return  this;
        }

        public CriteriaExt andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return  this;
        }

        public CriteriaExt andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return  this;
        }

        public CriteriaExt andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return  this;
        }

        public CriteriaExt andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return  this;
        }

        public CriteriaExt andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return  this;
        }

        public CriteriaExt andBirthdayIsNull() {
            addCriterion("birthday is null");
            return  this;
        }

        public CriteriaExt andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return  this;
        }

        public CriteriaExt andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return  this;
        }

        public CriteriaExt andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return  this;
        }

        public CriteriaExt andStatureIsNull() {
            addCriterion("stature is null");
            return  this;
        }

        public CriteriaExt andStatureIsNotNull() {
            addCriterion("stature is not null");
            return  this;
        }

        public CriteriaExt andStatureEqualTo(Float value) {
            addCriterion("stature =", value, "stature");
            return  this;
        }

        public CriteriaExt andStatureNotEqualTo(Float value) {
            addCriterion("stature <>", value, "stature");
            return  this;
        }

        public CriteriaExt andStatureGreaterThan(Float value) {
            addCriterion("stature >", value, "stature");
            return  this;
        }

        public CriteriaExt andStatureGreaterThanOrEqualTo(Float value) {
            addCriterion("stature >=", value, "stature");
            return  this;
        }

        public CriteriaExt andStatureLessThan(Float value) {
            addCriterion("stature <", value, "stature");
            return  this;
        }

        public CriteriaExt andStatureLessThanOrEqualTo(Float value) {
            addCriterion("stature <=", value, "stature");
            return  this;
        }

        public CriteriaExt andStatureIn(List<Float> values) {
            addCriterion("stature in", values, "stature");
            return  this;
        }

        public CriteriaExt andStatureNotIn(List<Float> values) {
            addCriterion("stature not in", values, "stature");
            return  this;
        }

        public CriteriaExt andStatureBetween(Float value1, Float value2) {
            addCriterion("stature between", value1, value2, "stature");
            return  this;
        }

        public CriteriaExt andStatureNotBetween(Float value1, Float value2) {
            addCriterion("stature not between", value1, value2, "stature");
            return  this;
        }
    }
}