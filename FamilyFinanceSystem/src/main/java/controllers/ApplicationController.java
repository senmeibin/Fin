/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.Fn00Dao;
import dao.UserDao;
import models.Fn00Dto;
import myFilters.SecureFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.Session;


@Singleton
public class ApplicationController {
    @Inject
    Fn00Dao fn00Dao;
    @Inject
    UserDao userDao;

    @FilterWith(SecureFilter.class)
    public Result index(@Param("year") String year) {

    	if(year == null){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    		year = sdf.format(new Date());
    	}

    	Fn00Dto dto= fn00Dao.search(year);
        return Results.html().render("year",year).render("dto",dto);
    }

    public Result login(Session session){
    	//session.clear();
    	return Results.html();
    }

    public Result checkLoginUser(Session session,
    		@Param("name") String name, @Param("password") String password){
    	Boolean checkOK= userDao.checkExist(name, password);
    	if(checkOK){
    		session.put("loginStatus", "OK");
    	}else{
    		session.put("loginStatus", "NG");
    	}
    	return Results.json().render(checkOK);
    }
}
