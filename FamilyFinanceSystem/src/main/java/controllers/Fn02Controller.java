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
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.Fn02Dao;
import models.Fn02Dto;
import models.Fn02DtoList;
import models.Fn02Record;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;


@Singleton
public class Fn02Controller {
    @Inject
    Fn02Dao fn02Dao;

    public Result fn02() {
        return Results.html();
    }

    public Result fn02Json(@Param("search_year") String search_year) {
    	List<Fn02Dto> retList = fn02Dao.search(search_year);
    	if(retList.isEmpty()){
    		// return empty data
    		retList.add(new Fn02Dto("",0));
    	}
    	return Results.json().render(retList);
    }

    public Result fn02SubmitAllItemsJson(Fn02DtoList fn02DtoList){
    	List<Fn02Record> updateList = fn02DtoList.parseDtoListToRecords();
    	List<Fn02Dto> retList = fn02Dao.update(fn02DtoList.getHeader_year(),updateList);

    	if(retList.isEmpty()){
    		// return empty data
    		retList.add(new Fn02Dto("",0));
    	}
    	return Results.json().render(retList);
    }
}
