/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.repository;

import com.kaibot.springrest.entity.Activity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author kaiak31
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long>{
    @Override
    public List<Activity> findAll();

    public Activity findByMerchantIdAndDescription(long merchantId, String description);
    
    
}
