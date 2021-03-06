/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.commands;

import io.fabric8.api.FabricService;
import io.fabric8.utils.TablePrinter;
import org.apache.felix.gogo.commands.Command;
import io.fabric8.api.FabricRequirements;
import io.fabric8.api.ProfileRequirements;
import io.fabric8.commands.support.RequirementsListSupport;

import java.io.PrintStream;
import java.util.List;

@Command(name = RequireProfileList.FUNCTION_VALUE, scope = RequireProfileList.SCOPE_VALUE, description = RequireProfileList.DESCRIPTION, detailedDescription = "classpath:status.txt")
public class RequireProfileListAction extends RequirementsListSupport {

    public RequireProfileListAction(FabricService fabricService) {
        super(fabricService);
    }

    @Override
    protected void printRequirements(PrintStream out, FabricRequirements requirements) {
        TablePrinter table = new TablePrinter();
        table.columns("profile", "# minimum", "# maximum", "depends on");
        List<ProfileRequirements> profileRequirements = requirements.getProfileRequirements();
        for (ProfileRequirements profile : profileRequirements) {
            table.row(profile.getProfile(),
                    getStringOrBlank(profile.getMinimumInstances()),
                    getStringOrBlank(profile.getMaximumInstances()),
                    getStringOrBlank(profile.getDependentProfiles()));
        }
        table.print();
    }

    public static String getStringOrBlank(Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }
}
