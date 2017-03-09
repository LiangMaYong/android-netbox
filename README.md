# Android-NetBox
this is android netbox library

## Gradle
```
compile 'com.liangmayong.android:netbox:$LatestVersion'
```
## Get start
Server
```
@BindURL(value = "http://github.com/api/", debug = "http://github.com/debug/api/")
@BindParams(key = "common_key", value = "common_value")
@BindDebugable(true)
public class DemoServer extends DefaultVolleyServer {

}
```
Exec
```
Netbox.server(DemoServer.class).path("login").param("username", "liangmayong").exec(...)
```
interfaceServer
```
public interface DemoInterface {

    @Mod(Method.POST)
    @Path("./uploadFile.php")
    void uploadFile(@File("data") FileParam file, OnNetboxListener listener);

    @Mod(Method.POST)
    @Path("./login.php")
    void login(@Key("username") String username, @Key("password") String password, OnNetboxListener listener);
}
```
Use interfaceServer
```
Netbox.server(DemoServer.class).interfaceServer(DemoInterface.class).login("liangmayong","***",...);
```
## LICENSE
```
Copyright 2016 LiangMaYong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
