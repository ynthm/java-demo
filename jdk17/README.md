一个 HTML 表单中的 enctype 有三种类型
- application/x-www-urlencoded
- multipart/form-data
- text-plain

默认情况下是  application/x-www-form-urlencoded，当表单使用 POST 请求时，数据会被以 x-www-urlencoded 方式编码到 Body 中来传送，
而如果 GET 请求，则是附在 url 链接后面来发送。

GET 请求只支持 ASCII 字符集，因此，如果我们要发送更大字符集的内容，我们应使用 POST 请求。

服务器端可以通过获取名为fileTypes的参数，然后将其拆分成字符数组，即可得到要保存文件的类型。
服务器段主要用到Servlet3.0的API，主要用到的方法有：
```java
request.getParameter(""); //获取客户端通过addTextBody方法添加的String类型的数据。
request.getPart(""); //获取客户端通过addBinaryBody、addPart、addTextBody方法添加的指定数据，返回Part类型的对象。
request.getParts(); //获取客户端通过addBinaryBody、addPart、addTextBody方法添加的所有数据，返回Collection<Part>类型的对象。
part.getName(); //获取上传文件的名称即上传时指定的key。
part.getSize(); //获取上传文件的大小单位为字节。
```
request.getParameter("fileTypes")方法来获取客户端上传的所有文件类型