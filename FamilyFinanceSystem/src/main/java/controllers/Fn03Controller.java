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
import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.Fn03Dao;
//import models.record.Fn03Record;
import models.Fn03Record;
import models.Fn03RecordList;
import ninja.Result;
import ninja.Results;


@Singleton
public class Fn03Controller {
    @Inject
    Fn03Dao fn03Dao;

    public Result fn03() {

        return Results.html();

    }

    public Result fn03Json() {
        return Results.json().render(fn03Dao.search());
    }

    public Result fn03AddItemJson(Fn03Record fn03Rec){
    	fn03Rec = fn03Dao.add(fn03Rec);
    	return Results.json().render(fn03Rec);
    }

    public Result fn03SubmitAllItemsJson(Fn03RecordList fn03RecList){
    	fn03Dao.update(fn03RecList.parseList());
    	return Results.json().render(fn03RecList);
    }
}
