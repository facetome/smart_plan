# smart_plan
this is a simple library can search database from your android device which can not root,but it is just for debug mode,when you  pack your app, you may remove it from your project.
## 1.how to dependence the library
you can download the project and import the library to you project, this is very simple , i think you can do it.
<br/>`compile project(':library')` and sync you your proect by gradle.<br/>
## 2.how to use
now you have configuration you project and we can coding. is very simple ,you just coding this in your first activity.<br/>
`SmartPlan.startPlan(MainActivity.this);` in onCreate() and <br/> ` SmartPlan.stopPlan(MainActivity.this);` in onDestory() <br/>
 and then, you need to coding this in you AndroidManifest.xml <br/>
`<service android:name="com.basic.android.library.server.AndroidServerService"android:exported="false" />`
</br>
## 3.how to search the data.
ok, we have complete our work in android and we can run the main.html in web_plan<br/>
input your devices ip in editext and you can see your database and table in web！<br/>
at the last!is very important,you must keep your android devices and the html web run in a same network(Local area network)! 
