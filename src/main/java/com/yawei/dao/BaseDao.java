package com.yawei.dao;


import com.yawei.util.Page;
import com.yawei.util.SearchParam;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.ResultTransformer;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDao<T,PK extends Serializable> {
    @Inject
    private SessionFactory sessionFactory;
    private Class<?> entityClass;

    public BaseDao(){
        ParameterizedType parameterizedType= (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass= (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity){
        getSession().saveOrUpdate(entity);
    }

    public void delete(T entity){
        getSession().delete(entity);
    }

    public T findById(PK id){
       return (T) getSession().get(entityClass,id);
    }

    public void delete(PK id){
        delete(findById(id));
    }

    public List<T> findAll(){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.addOrder(Order.desc("id"));
        return criteria.list();
    }

    public Long count(){
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public Long count(Criteria criteria){
        ResultTransformer resultTransformer = criteria.ROOT_ENTITY;
        criteria.setProjection(Projections.rowCount());
        Long count= (Long) criteria.uniqueResult();

        criteria.setProjection(null);
        criteria.setResultTransformer(resultTransformer);
        return count;
    }

    public Page<T> findByPageNo(Integer pageNo,Integer pageSize){
        Integer totalSize= count().intValue();
        Page<T> page = new Page<>(pageNo,pageSize,totalSize);

        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(page.getPageSize());

        List<T> result = criteria.list();
        page.setItems(result);
        return page;
    }

    public Page<T> findByPageNo(Criteria criteria,Integer pageNo, Integer pageSize, List<SearchParam> searchParamList){
        criteria = buildCriteriaBySearchParam(searchParamList);
        Integer totalSize = count(criteria).intValue();
        Page<T> page = new Page<>(pageNo,pageSize,totalSize);
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(page.getPageSize());

        List<T> result = criteria.list();
        page.setItems(result);
        return page;
    }

    public Page<T> findByPageNo(Integer pageNo, Integer pageSize, List<SearchParam> searchParamList){
        Criteria criteria = buildCriteriaBySearchParam(searchParamList);
        Integer totalSize = count(criteria).intValue();
        Page<T> page = new Page<>(pageNo,pageSize,totalSize);
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(page.getPageSize());

        List<T> result = criteria.list();
        page.setItems(result);
        return page;
    }

    private Criteria buildCriteriaBySearchParam(Criteria criteria,List<SearchParam> searchParamList) {
        for (SearchParam searchParam:searchParamList){
            String protertyName = searchParam.getProtertyName();
            String type = searchParam.getType();
            Object value = searchParam.getValue();

            if(protertyName.contains("_or_")){
                String[] nameArray=protertyName.split("_or_");
                Disjunction disjunction = Restrictions.disjunction();
                for(String name:nameArray){
                    Criterion criterion = buildCondition(name,type,value);
                    disjunction.add(criterion);
                }
                criteria.add(disjunction);
            }else {
                Criterion criterion = buildCondition(protertyName, type, value);
                criteria.add(criterion);
            }
        }
        return criteria;
    }

    private Criteria buildCriteriaBySearchParam(List<SearchParam> searchParamList) {
        Criteria criteria = getSession().createCriteria(entityClass);
        return buildCriteriaBySearchParam(criteria,searchParamList);
    }

    private Criterion buildCondition(String protertyName, String type, Object value) {
        if("eq".equalsIgnoreCase(type)){
            return Restrictions.eq(protertyName,value);
        }else if("like".equalsIgnoreCase(type)){
            return Restrictions.like(protertyName,value.toString(), MatchMode.ANYWHERE);
        }else if("ge".equalsIgnoreCase(type)){
            return Restrictions.ge(protertyName,value);
        }else if("gt".equalsIgnoreCase(type)){
            return Restrictions.gt(protertyName,value);
        }else if("le".equalsIgnoreCase(type)){
            return Restrictions.le(protertyName,value);
        }else if("lt".equalsIgnoreCase(type)){
            return Restrictions.lt(protertyName,value);
        }
        return null;
    }

}
