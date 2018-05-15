/*
 *******************************************************************************
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.eclipse.microprofile.fault.tolerance.tck.illegalConfig;

import javax.enterprise.inject.spi.DefinitionException;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.ShouldThrowException;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

public class IncompatibleFallbackMethodTest extends Arquillian {
    private
    @Inject
    FallbackMethodClient fallbackMethodClient;

    @Deployment
    @ShouldThrowException(value = DefinitionException.class)
    public static WebArchive deployAnotherApp() {
        JavaArchive testJar = ShrinkWrap
                .create(JavaArchive.class, "ftInvalid.jar")
                .addClasses(FallbackMethodClient.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .as(JavaArchive.class);

        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "ftInvalidFallbackMethod.war")
                .addAsLibrary(testJar);
        return war;
    }

    /**
     * Test that the deployment of a FallbackHandler with an invalid Fallback Method leads to a DeploymentException.
     * 
     * A Service is annotated with the IncompatibleFallbackMethodHandler. While the Service returns an
     * Integer, the IncompatibleFallbackMethodHandler's Fallback Method returns a String.
     */
    @Test
    public void test() {
       
    }
}
