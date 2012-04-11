package com.googlecode.wicketwebbeans.databinder;

import net.databinder.models.hib.CriteriaBuilder;

public interface ICriteriaBuilderDelegate extends CriteriaBuilder
{
	public void addCriteriaBuilder(CriteriaBuilder criteriaBuilder);
	public void removeCriteriaBuilder(CriteriaBuilder criteriaBuilder);
	public Iterable<CriteriaBuilder> criteriaBuilders();
}
