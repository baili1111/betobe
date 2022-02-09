package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/1 14:51:26
 * @description
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {



}
