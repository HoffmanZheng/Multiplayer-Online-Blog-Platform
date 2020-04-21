# 后端接口规范

约定接口一般包括以下数据

1. 当前接口的路径是什么？ 如 `/auth/register`
2. 当前接口提交数据的类型是什么? 如
   - `GET` 获取数据
   - `POST` 提交或者创建
   - `PATCH` 修改数据，部分修改
   - `DELETE` 删除数据
   - `PUT` 修改数据，整体替换原有数据
3. 参数类型/格式，比如是 json 格式，还是 `application/x-www-form-urlencoded`的数据
4. 参数字段，及限制条件
5. 返回成功的数据格式
6. 返回失败的数据格式

后端接口线上地址根路径： [http://blog-server.hunger-valley.com](http://blog-server.hunger-valley.com/)

下面我们和后端做以下接口约定，开发阶段可以用 postman 或者 [curl 命令](https://gist.github.com/subfuzion/08c5d85437d5d4f00e58)测试接口

## 认证相关

### `POST /auth/register`

功能: 用户注册

提交参数

- 参数类型:`Content-Type: application/x-www-form-urlencoded;charset=utf-8`
- 参数字段
  - `username` : 用户名, 长度1到15个字符，只能是字母数字下划线中文
  - `password` : 密码, 长度6到16个任意字符

返回数据

- 失败
  - 返回格式 `{"status": "fail", "msg": "错误原因"}`
- 成功
  - 返回格式

```javascript
{
  "status": "ok",
  "msg": "注册成功",
  "data": {
    "id": 1,
    "username": "hunger",
    "avatar": "http://avatar.com/1.png",
    "updatedAt": "2017-12-27T07:40:09.697Z",
    "createdAt": "2017-12-27T07:40:09.697Z"
  }
}
```

测试

```bash
# -d 是用来传递数据
# 对于 POST 和 PUT 可以：  -X POST， 对于 GET，不加 -X
curl -d "username=hunger1&password=123456" -X POST "http://localhost:3000/auth/register"
```

#### `POST /auth/login`

功能: 用户登录

提交参数

- 参数类型:`Content-Type: application/x-www-form-urlencoded;charset=utf-8`
- 参数字段
  - `username` : 用户名, 长度1到15个字符，只能是字母数字下划线中文
  - `password` : 密码, 长度6到16个任意字符

返回数据

- 失败
  - 返回格式 `{"status": "fail", "msg": "用户不存在"}` 或者 `{"status": "fail", "msg": "密码不正确"}`
- 成功
  - 返回格式

```javascript
{
  "status"："ok",
  "msg": "登录成功",
  "data": {
    "id": 1,
    "username": "hunger",
    "avatar: "头像 url",
    "createdAt": "2017-12-27T07:40:09.697Z",
    "updatedAt": "2017-12-27T07:40:09.697Z"
  }
}
```

测试命令

```
# -i 可以展示响应头
# 会发现响应头里有 setCookie 信息，得到 cookie

curl -d "username=hunger1&password=123456" "http://localhost:3000/auth/login" -i
```

#### `GET /auth`

功能: 判断用户是否登录

提交参数: 无

返回数据

- 已经登录的情况

```javascript
{
  "status": "ok"
  "isLogin": true,
  "avatar": "http://avatar.com/1.png",
  "data": {
    "id": 1,
    "username": "hunger",
    "updatedAt": "2017-12-27T07:40:09.697Z",
    "createdAt": "2017-12-27T07:40:09.697Z"
  }
}
```

- 没有登录的情况

```javascript
{
  "status": "ok"
  "isLogin": false
}
```

测试命令

```
#先通过登录接口获取 cookie，带上 cookie 就能测试登录

curl "http://localhost:3000/auth" -b "connect.sid=s%3AmeDbrn03UtTM8fqChaPQ20wmWlnKeHiu.e3uMtu7j1zQ1iNeaajCmxkYYGQ%2FyHV1ZsozMvZYWC6s"
```

#### `GET /auth/logout`

功能: 注销登录

提交参数: 无

返回数据:

- 失败
  - 返回格式 `{"status": "fail", "msg": "用户尚未登录"}`
- 成功
  - 返回格式 `{"status": "ok", "msg": "注销成功"}`

测试命令

```
curl "http://localhost:3000/auth/logout" -b "connect.sid=s%3AmeDbrn03UtTM8fqChaPQ20wmWlnKeHiu.e3uMtu7j1zQ1iNeaajCmxkYYGQ%2FyHV1ZsozMvZYWC6s"
```

## 博客相关

#### `GET /blog`

功能: 获取博客列表

提交参数:

- `page`: 页码，不传默认 page 为1。如果设置该参数则获取博客列表的第 page 页博客列表
- `userId`: 用户 id，不传则获取全部用户的数据，如果设置则获取某个用户的博客列表
- `atIndex`: 是否展示在首页，传递 `true`则只得到显示到首页的博客列表，不传得到全部类型(包括展示到首页和不展示到首页)的博客列表，`false`得到不展示到首页的列表

如 `/blog?page=2&userId=1` 获取属于用户1的第2页博客列表

返回数据:

- 失败
  - `{"status": "fail", "msg": "系统异常"}`
- 成功
  - 返回格式

```javascript
{
  "status": "ok",
  "msg": "获取成功",
  "total": 200, //全部博客的总数
  "page": 2, //当前页数
  "totalPage": 10, // 总页数
  "data": [
    { 
      "id": 1,                 //博客 id
      "title": "博客标题",       
      "description": "博客内容简要描述", 
      "user": {
        "id": 100, //博客所属用户 id,
        "username": "博客所属用户 username",
        "avatar": "头像"
      },
      "createdAt": "2018-12-27T08:22:56.792Z",   //创建时间
      "updatedAt": "2018-12-27T08:22:56.792Z"  //更新时间
    },
    ...
  ]
}
```

测试命令

```bash
curl "http://localhost:3000/blog?page=1&userId=1"
curl "http://localhost:3000/blog?page=1"
curl "http://localhost:3000/blog"
```

#### `GET /blog/:blogId`

功能: 获取id 为 blogId 的博客详情， 如 `/blog/1`

提交参数:

无

返回数据:

- 失败
  - `{"status": "fail", "msg": "系统异常"}`
- 成功
  - 返回格式

```javascript
{
  "status": "ok",
  "msg": "获取成功",
  "data": { 
      "id": 1,                 //博客 id
      "title": "博客标题",       
      "description": "博客内容简要描述", 
      "content": "博客内容，字比较多",
      "user": {
        "id": 100, //博客所属用户 id,
        "username": "博客所属用户 username",
        "avatar": "头像"
      },
      "createdAt": "2018-12-27T08:22:56.792Z",   //创建时间
      "updatedAt": "2018-12-27T08:22:56.792Z"  //更新时间
    }
}
```

#### `POST /blog`

功能: 创建博客

提交参数

- 参数类型:`Content-Type: application/x-www-form-urlencoded; charset=utf-8`
- 参数字段
  - `title` : 博客标题, 博客标题不能为空，且不超过100个字符
  - `content` : 博客内容, 博客内容不能为空，且不超过10000个字符
  - `description`: 博客内容简要描述,可为空，如果为空则后台自动从content 中提取

返回数据

- 失败
  - 返回格式 `{"status": "fail", "msg": "登录后才能操作"}`
- 成功
  - 返回格式

```javascript
{
  "status": "ok",
  "msg": "创建成功",
  "data": { 
      "id": 1,                 //博客 id
      "title": "博客标题",   
      "description":  "博客内容简要描述",   
      "contnet": "博客内容",
      "user": {
        "id": 100, //博客所属用户 id,
        "username": "博客所属用户 username",
        "avatar": "头像url"
      },
      "createdAt": "2018-12-27T08:22:56.792Z",   //创建时间
      "updatedAt": "2018-12-27T08:22:56.792Z"   //更新时间
    }
}
```

测试命令

```
curl -d "title=hello&content=world&description=jirengu" -X POST "http://localhost:3000/blog" -b "connect.sid=s%3AdyZh-z5fqPU_ThG9Qn8nGD6euI0UI75e.8uso0k4P6WzqWv02iQCUwxbUML2RdlOCnpKp7RSJpj0"
```

#### `PATCH /blog/:blogId`

功能: 修改博客 id 为:blogId 的博客

范例: `/blog/1`

提交参数

- 参数类型:`Content-Type: application/x-www-form-urlencoded; charset=utf-8`
- 参数字段
  - `title` : 博客标题, 可选
  - `content` : 博客内容, 可选
  - `description`: 博客内容简要描述, 可选
  - `atIndex`: `true/false`， 展示到首页/从首页异常, 可选

返回数据

- 失败
  - 返回格式
    - `{"status": "fail", "msg": "登录后才能操作"}`
    - `{"status": "fail", "msg": "博客不存在"}`
    - `{"status": "fail", "msg": "无法修改别人的博客"}`
- 成功
  - 返回格式

```
{
  "status": "ok",
  "msg": "修改成功",
  "data": { 
      "id": 1,                 //博客 id
      "title": "博客标题",   
      "description":  "博客内容简要描述",   
      "contnet": "博客内容",
      "user": {
        "id": 100, //博客所属用户 id,
        "username": "博客所属用户 username",
        "avatar": "头像url"
      },
      "createdAt": "2018-12-27T08:22:56.792Z",   //创建时间
      "updatedAt": "2018-12-27T08:22:56.792Z"   //更新时间
    }
}
```

测试命令

```bash
curl -d "title=hello100&content=world1&description=jirengu2234444444&atIndex=true" -X PATCH "http://localhost:3000/blog/12" -b "connect.sid=s%3At_9V2bMXA7U9oSAmr1dhRXpdRPAsNM2B.jlpWgkwiWdpgTjexeTHGNydt8gvc%2F%2BEkJpQ9yaAmTg0"
```

#### `DELETE /blog/:blogId`

功能: 删除博客 id 为:blogId 的博客

提交参数：无

返回数据

- 失败

  - 返回格式范例
    - `{"status": "fail", "msg": "登录后才能操作"}`
    - `{"status": "fail", "msg": "博客不存在"}`
    - `{"status": "fail", "msg": "无法删除别人的博客"}`

- 成功

  - 返回格式

    ```
    {
    "status": "ok",
    "msg": "删除成功"
    ```

测试命令

```bash
curl -X DELETE "http://localhost:3000/blog/12" -b "connect.sid=s%3AG_Chytg2F0RLWh2wTSCdLWVxpNg1MWWb.nPuMcgyMN6zxuxjSkyu8qSqM1boruol1Nce7egaXrPw"
```