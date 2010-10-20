This is a small/simple plugin that installs a workaround for [GRAILS-6370](http://jira.codehaus.org/browse/GRAILS-6370 "[#GRAILS-6370] ClassCastException when reloading classes with aspects (e.g. @Transactional) - jira.codehaus.org") which prevents beans using `@Transactional` (or other AOP annotations such as `@Cacheable` from the [springcache](http://gpc.github.com/grails-springcache/ "Springcache Grails Plugin @ GitHub") plugin) from being reloadable. Without these plugin, you will be greeted with a `ClassCastException` when trying to reload these classes.

Note that this plugin is only required for versions of Grails preceeding 1.3.6 as this plugin is essentially incorporated into 1.3.6 and greater.

## Usage 

All you need to do is install it:

    grails install-plugin aop-reloading-fix

## How it works

This plugin modifies with way AOP proxies are created in a reloadable environment to make them reload capable. It has no effect when running in a non reloadable environment such as production.

## More Information

If you want the gritty details, you can follow [GRAILS-6370](http://jira.codehaus.org/browse/GRAILS-6370 "[#GRAILS-6370] ClassCastException when reloading classes with aspects (e.g. @Transactional) - jira.codehaus.org")