/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaibot.springrest.repository;

import com.kaibot.springrest.entity.Activity;
import com.kaibot.springrest.entity.Slot;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaiak31
 */
@Repository
public interface SlotRepository  extends CrudRepository<Slot, Long>{
    @Query("select sl from Slot sl where sl.activity.id = ?1 and (sl.date BETWEEN ?2 AND ?3)")
    public List<Slot> findByActivityIdAndDateRange(long activityId, Date start, Date end);

    public List<Slot> findByActivityId(long activityId);
    @Override
    public List<Slot> findAll();
}


