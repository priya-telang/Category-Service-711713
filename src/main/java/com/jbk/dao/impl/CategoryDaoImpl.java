package com.jbk.dao.impl;

import javax.persistence.RollbackException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.dao.CategoryDao;

import com.jbk.entity.CategoryEntity;

import com.jbk.exception.ResourceAlreadyExistsException;
import com.jbk.exception.ResourceNotExistsException;
import com.jbk.exception.SomethingWentWrongException;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	boolean isAdded=false;
	@Override
	public boolean addCategory(CategoryEntity categoryEntity) {
		
		try {
		Session session= sessionFactory.openSession();
			
		//check its exists or not
		CategoryEntity dbEntity=getCategoryById(categoryEntity.getCategoryId());
		
		if(dbEntity==null) {
		   
			session.save(categoryEntity);//insert into
			session.beginTransaction().commit();
			isAdded=true;
		}else {
			throw new ResourceAlreadyExistsException("Category Already Exists with Id:" + categoryEntity.getCategoryId());
		}
			
		}catch(RollbackException e) {
			e.printStackTrace();
			throw new SomethingWentWrongException("Something went wrong in during add category,check unique field");
		}
		return isAdded;
	}
	@Override
	public CategoryEntity getCategoryById(long categoryId) {
		
		CategoryEntity categoryEntity=null;
		
		try {
			
			Session session = sessionFactory.openSession();
        
			categoryEntity= session.get(CategoryEntity.class,categoryId);//select * from supplier where supplierId=3;
		
		}catch(HibernateException e) {
			
			throw new SomethingWentWrongException("Connection failure");
		}
		return categoryEntity;
	}
	//delete
	//supplierEntity=session.get(SupplierEntity.class,supplierId);
	//check
	//not null then delete
	//if null then throw exception
	@Override
	public boolean updateCategory(CategoryEntity categoryEntity) {
		boolean isUpdated=false;
		try {
			Session session=sessionFactory.openSession();
			
CategoryEntity dbCategory = getCategoryById(categoryEntity.getCategoryId());
			if(dbCategory!=null) {
				session.update(categoryEntity);
				session.beginTransaction().commit();
				isUpdated = true;
			}else {
				
				throw new ResourceNotExistsException("Category Not Exists with ID:" +categoryEntity.getCategoryId());
			}
			
			
		}
		catch(Exception e) {
			
			throw new SomethingWentWrongException("Something Went Wrong During Update Category");
		
		}
		
		return isUpdated;
	}
	

	
}

