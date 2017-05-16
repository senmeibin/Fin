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

import dao.Fn01Dao;
import models.Fn01DtoList;
import myFilters.SecureFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;


@Singleton
@FilterWith(SecureFilter.class)
public class Fn01Controller {
    @Inject
    Fn01Dao fn01Dao;

    public Result fn01(@Param("search_ym") String search_ym) {
    	if(search_ym == null){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    		search_ym = sdf.format(new Date());
    	}
        return Results.html().render("search_ym",search_ym);
    }

    public Result fn01Json(@Param("search_ym") String search_ym) {
    	return Results.json().render(fn01Dao.search(search_ym));
    }

    public Result fn01_newjson(@Param("search_ym") String search_ym,
    							@Param("header_ym") String header_ym) {
    	String ym = (search_ym == null) ? header_ym : search_ym;
    	return Results.json().render(fn01Dao.newRecords(ym));
    }

    public Result fn01SubmitAllItemsJson(Fn01DtoList fn01DtoList){
    	fn01Dao.update(fn01DtoList.parseDtoListToRecords());
    	return Results.json().render(fn01DtoList);
    }
}
