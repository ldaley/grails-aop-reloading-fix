/*
 * Copyright 2010 Luke Daley
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
import grails.util.Environment
import grails.util.Metadata

class AopReloadingFixGrailsPlugin {
	
	def version = "0.1"
	def grailsVersion = "* > 1.3.5"
	def dependsOn = [:]
	def pluginExcludes = ["grails-app/**/*"]

	def author = "Luke Daley"
	def authorEmail = "ld@ldaley.com"
	def title = "AOP Reloading Fix"
	def description = "A back-ported fix for GRAILS-6370 - reloading classes with aspects"
	def documentation = "http://grails.org/plugin/aop-reloading-fix"

	def doWithSpring = {
		if (isEnvironmentClassReloadable()) {
			def grailsConfig = application.config.grails
			def springConfig = grailsConfig.spring
			if(springConfig.disable.aspectj.autoweaving) {
				log.warn("not fixing aop reload because aspectj autoweaving is disabled")
			} else {
				def name = getAutoProxyCreatorBeanName()
				def clazz = getAutoProxyCreatorClass()
				
				// we are intending to replace the auto proxy creator created by the core grails plugin. 
				"$name"(clazz)
			}
		} else {
			log.debug("not fixing aop reload because the environment is not reloadable")
		}
	}

	def getAutoProxyCreatorBeanName() {
		"org.springframework.aop.config.internalAutoProxyCreator"
	}
	
	def getAutoProxyCreatorClass() {
		getClass().classLoader.loadClass("grails.plugin.aopreloadingfix.ClassLoaderPerProxyGroovyAwareAspectJAwareAdvisorAutoProxyCreator")
	}
	
	static isEnvironmentClassReloadable() {
		def env = Environment.current
		env.reloadEnabled || (Metadata.current.getApplicationName() == "aop-reloading-fix" && env == Environment.TEST)
	}
}
