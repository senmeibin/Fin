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
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.Fn04Dao;
import models.Fn04Record;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;


@Singleton
public class Fn04Controller {
    @Inject
    Fn04Dao fn04Dao;

    public Result fn04() {

        return Results.html();

    }

    public Result fn04Json(@Param("search_year") String search_year) {
    	List<Fn04Record> list = fn04Dao.search(search_year);
        return Results.json().render(list);
    }

    public Result fn04UpdateAll(Fn04Record[] fn04Arr){
    	List<Fn04Record> fn04RecList = Arrays.asList(fn04Arr);
    	List<Fn04Record> list = fn04Dao.update(fn04RecList);
    	return Results.json().render(list);
    }
}
