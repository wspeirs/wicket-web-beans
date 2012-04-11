package com.googlecode.wicketwebbeans.databinder;

import java.util.Set;
import java.util.LinkedHashSet;
import net.databinder.models.hib.CriteriaBuilder;
import org.hibernate.Criteria;

public class CriteriaBuilderDelegate implements ICriteriaBuilderDelegate
{
	private static final long serialVersionUID = 1282855853286535520L;

	private Set<CriteriaBuilder> builders = new LinkedHashSet<CriteriaBuilder>();
	
	public void addCriteriaBuilder(CriteriaBuilder criteriaBuilder)
	{
		builders.add(criteriaBuilder);
	}

	public Iterable<CriteriaBuilder> criteriaBuilders()
	{
		return builders;
	}

	public void removeCriteriaBuilder(CriteriaBuilder criteriaBuilder)
	{
		builders.remove(criteriaBuilder);
	}

	public void build(Criteria criteria)
	{
		for(CriteriaBuilder builder: criteriaBuilders())
		{
			builder.build(criteria);
		}
	}

}
