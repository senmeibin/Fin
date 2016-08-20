/**
 * Copyright (C) 2012 the original author or authors.
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

package conf;




import controllers.ApplicationController;
import controllers.Fn01Controller;
import controllers.Fn02Controller;
import controllers.Fn03Controller;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

public class Routes implements ApplicationRoutes {
    @Override
    public void init(Router router) {
        router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");

        ///////////////////////////////////////////////////////////////////////
        // Fn03 route
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/fn03.html").with(Fn03Controller.class, "fn03");
        router.POST().route("/fn03.json").with(Fn03Controller.class, "fn03Json");
        router.POST().route("/fn03_addItem.json").with(Fn03Controller.class, "fn03AddItemJson");
        router.POST().route("/fn03_submitAllItems.json").with(Fn03Controller.class, "fn03SubmitAllItemsJson");

        ///////////////////////////////////////////////////////////////////////
        // Fn01 route
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/fn01.html").with(Fn01Controller.class, "fn01");
        router.POST().route("/fn01.json").with(Fn01Controller.class, "fn01Json");
        router.POST().route("/fn01_new.json").with(Fn01Controller.class, "fn01_newjson");
        router.POST().route("/fn01_submitAllItems.json").with(Fn01Controller.class, "fn01SubmitAllItemsJson");

        ///////////////////////////////////////////////////////////////////////
        // Fn02 route
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/fn02.html").with(Fn02Controller.class, "fn02");
        router.POST().route("/fn02.json").with(Fn02Controller.class, "fn02Json");
        router.POST().route("/fn02_submitAllItems.json").with(Fn02Controller.class, "fn02SubmitAllItemsJson");

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
